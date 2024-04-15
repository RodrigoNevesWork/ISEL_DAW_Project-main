package DAW.BattleShip.domain

import DAW.BattleShip.domain.ErrorHandling.GameAlreadyOver
import DAW.BattleShip.domain.ErrorHandling.GameNotBegun
import BattleShip.API.domain.positions.Position
import java.time.Instant
import java.util.UUID

data class Game(
                val playerA: Int,
                val playerB : Int,
                val boardA: Board,
                val boardB: Board,
                val state: GameState = GameState.NOT_BEGUN,
                val gameId : UUID,
                val created : Instant,
                val updated : Instant,
                val deadLine : Instant? = null
                ){
    val isEnded : Boolean
        get() = state == GameState.PLAYER_A_WON || state == GameState.PLAYER_B_WON
}

enum class GameState{
    NOT_BEGUN,
    PLAYER_A,
    PLAYER_B,
    PLAYER_A_WON,
    PLAYER_B_WON;
}

fun GameState.toPlayer(game : Game) : Int =
    when(this) {
        GameState.PLAYER_A   -> game.playerA
        GameState.PLAYER_A_WON -> game.playerA
        GameState.PLAYER_B -> game.playerB
        GameState.PLAYER_B_WON -> game.playerB
        else -> throw GameNotBegun()
    }


fun Game.otherBoard(player: Int) : Board = if(playerA == player)  boardB else boardA

fun Game.otherPlayer(player : Int) : Int = if(playerA == player)  playerB else playerA

fun Int.toGameState(game: Game ,win : Boolean = false) : GameState {
    return if(win){
        if(this == game.playerA)  GameState.PLAYER_A_WON else GameState.PLAYER_B_WON
    } else{
        if(this == game.playerA) return GameState.PLAYER_A else GameState.PLAYER_B
    }

}

fun Game.print(){
    println("------------------BOARD A ------------")

    boardA.grid.forEach {
        print("$it ")
        println(it.position)
    }

    println("------------------BOARD B ------------")

    boardB.grid.forEach {
        print("$it ")
        println(it.position)
    }

    println(state)

}

fun Game.changeState(board : Board, position : Position, player : Int) : GameState{
   val possibleWinner = board.lost()
    if(possibleWinner) return player.toGameState(this, true)
    return if (board.isMissShot(position)) otherPlayer(player).toGameState(this) else player.toGameState(this)
}

/**
 * @param player the score can change so we only use the identifier
 */
fun Game.shot(player : Int, position : Position) : Game{
    if(this.isEnded) throw GameAlreadyOver()
    val otherBoard = otherBoard(player)
    val boardRes = otherBoard.play(position,player, state.toPlayer(this))
    val playerABoard = if(playerA != player) boardRes else boardA
    val playerBBoard = if(playerB != player) boardRes else boardB
    boardRes.grid.forEach {
        if(it is StateShip) println(" position -> $it.position || state ->${it.javaClass}")
    }
    return copy(
        state = changeState(boardRes, position, player),
        boardA = playerABoard,
        boardB = playerBBoard,
        updated = RealClock.now()
    )
}



fun Game.placeAllShips(ships : List<OwnShip>, player : Int) : Game =
     if(player == playerA) {
        this.copy(
            boardA = boardA.placeAllShips(ships),
            state = if(boardB.grid.isEmpty()) GameState.NOT_BEGUN else GameState.PLAYER_A,
            updated = RealClock.now()
        )
    } else{
        this.copy(boardB = boardB.placeAllShips(ships),
            state = if(boardA.grid.isEmpty()) GameState.NOT_BEGUN else GameState.PLAYER_A,
            updated = RealClock.now()
        )
    }



