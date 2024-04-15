package DAW.BattleShip.services

import BattleShip.API.domain.positions.Position
import DAW.BattleShip.domain.*
import DAW.BattleShip.domain.ErrorHandling.*
import DAW.BattleShip.http.model.GameOutput
import DAW.BattleShip.http.model.GameOutputState
import DAW.BattleShip.http.model.OutputShip
import DAW.BattleShip.http.model.PartialGameOutput
import DAW.BattleShip.repository.TransactionManager
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class GamesServices(private val transactionManager: TransactionManager, private val clock : Clock) {

    fun getGame(gameID: UUID) : Game?{
        return transactionManager.run {
            it.gamesRepository.read(gameID)
        }
    }

    fun startNewGame(player : Int, points : Double, size: Int, deadline: Instant? = null) : LobbyResult =
         transactionManager.run {
            LobbyResult(it.lobbyRepository.checkLobby(player,points, size, deadline)?.let { id -> createGame(player, id, size, deadline) })
        }


    private fun createGame(playerA: Int, playerB: Int, size: Int, deadline: Instant? = null): UUID {
        val game = Game(
            playerA,
            playerB,
            Board(emptyList(), size),
            Board(emptyList(), size),
            GameState.NOT_BEGUN,
            created = clock.now(),
            updated = clock.now(),
            deadLine = deadline,
            gameId = UUID.randomUUID()
         )
        transactionManager.run {
            it.gamesRepository.create(game)
        }
        return game.gameId
    }

    fun quitGame(player: Int, game : Game) {
        transactionManager.run {

           val winner : GameState =
               if (game.playerA == player ) GameState.PLAYER_B_WON
               else if(game.playerB == player) GameState.PLAYER_A_WON
               else throw Unauthorized()

           it.gamesRepository.update(
                game.copy(state = winner)
            )
        }


    }

    fun placeShips(player : Int, ships: List<OwnShip>, game: Game) : GameOutput {
        return transactionManager.run {

            if (game.state != GameState.NOT_BEGUN) throw GameAlreadyBegun()

            val newGame = game.placeAllShips(ships, player).copy(updated = clock.now())

            it.gamesRepository.update(newGame)

            newGame.toGameOutput(player)

        }
    }

    //points of winner first then loser
    private fun pointsAfterGame(winner : Double, loser : Double ) : Pair<Double, Double>{
        return Rating.newRatings(winner,loser)
    }


    private fun Board.toOutputBoard() : List<OutputShip>{
        val list = mutableListOf<OutputShip>()

        this.grid.forEach {
              list.add(OutputShip(it,it.javaClass.simpleName))
        }
        return list
    }



    private fun Board.toEnemyShip() : List<OutputShip> {
        val list = mutableListOf<OutputShip>()

         this.grid.forEach {
             if(it !is OwnShip)
                list.add(OutputShip(it,it.javaClass.simpleName))
        }
        return list
    }

    private fun repeatedMoveOnBoard(board : List<Tile>, position : Position) : Boolean{
        return board.any{
            it !is OwnShip && it.position == position
        }
    }

    private fun isMoveRepeated(player : Int, game : Game, position: Position) : Boolean{
        if(game.playerA == player){
            return repeatedMoveOnBoard(game.boardB.grid,position)
        }else if(game.playerB == player){
            return repeatedMoveOnBoard(game.boardA.grid,position)
        }
        throw Unauthorized()
    }

    private fun Game.toGameOutputState(id: Int): GameOutputState {
        return when (state) {
            GameState.NOT_BEGUN -> GameOutputState.NOT_BEGUN
            GameState.PLAYER_A -> if (id == playerA) GameOutputState.YOUR_TURN else GameOutputState.OPPONENT_TURN
            GameState.PLAYER_B -> if (id == playerB) GameOutputState.YOUR_TURN else GameOutputState.OPPONENT_TURN
            GameState.PLAYER_A_WON -> if (id == playerA) GameOutputState.VICTORY else GameOutputState.DEFEAT
            GameState.PLAYER_B_WON -> if (id == playerB) GameOutputState.VICTORY else GameOutputState.DEFEAT
        }
    }


    private fun Game.toGameOutput(player : Int) : GameOutput {
        return if(playerA == player)
                    GameOutput(boardA.toOutputBoard(), boardB.toEnemyShip(),toGameOutputState(player), boardA.size)
               else
                    GameOutput(boardB.toOutputBoard(),boardA.toEnemyShip(),toGameOutputState(player),boardA.size)
    }

    fun shot(player : Int, game: Game, position: Position) : GameOutput {
        return transactionManager.run {

            if(isMoveRepeated(player,game,position)) throw BadPlay()

            val newGame = game.shot(player, position)

            if(newGame.isEnded) {
                val thisPlayer = it.playersRepository.getPlayerById(player) ?: throw UserNotExists()

                val idOtherPlayer = if(player == newGame.playerA ) newGame.playerB else newGame.playerA
                val otherPlayer = it.playersRepository.getPlayerById(idOtherPlayer) ?: throw UserNotExists()

                val newPoints = pointsAfterGame(thisPlayer.points, otherPlayer.points)

                it.playersRepository.updatePoints(thisPlayer.id, newPoints.first)
                it.playersRepository.updatePoints(otherPlayer.id, newPoints.second)
            }

            it.gamesRepository.update(newGame.copy(updated = clock.now()))
            newGame.toGameOutput(player)
        }


    }



    fun checkGame(player : Int, game : Game) : GameOutput {
        return transactionManager.run {

            val ownShips =
                if (player == game.playerA)
                game.boardA.toOutputBoard()
            else
                game.boardB.toOutputBoard()

            val enemyShips =
                if (player == game.playerA)
                game.boardB.toEnemyShip()
            else
                game.boardA.toEnemyShip()

            GameOutput(
                ownBoard = ownShips,
                enemyBoard =  enemyShips,
                state = game.toGameOutputState(player),
                size = game.boardA.size
            )
        }
    }

    fun checkOwnFleet(player: Int, game : Game): PartialGameOutput {
        return transactionManager.run {

            val ownShip =
                if (player == game.playerA)
                    game.boardA.toOutputBoard()
                else
                    game.boardB.toOutputBoard()

            PartialGameOutput(ownShip,game.toGameOutputState(player),game.boardA.size)
        }
    }

    fun checkOpponentFleet(player: Int, game : Game): PartialGameOutput {
        return transactionManager.run {

            val enemyShips = if (player == game.playerA)
                game.boardB.toEnemyShip()
            else game.boardA.toEnemyShip()

            PartialGameOutput(enemyShips,game.toGameOutputState(player),game.boardA.size)
        }
    }

    fun checkLobby(playerID : Int) : UUID?{
        return transactionManager.run {
            it.lobbyRepository.checkIfEnterGame(playerID)
        }
    }
}





