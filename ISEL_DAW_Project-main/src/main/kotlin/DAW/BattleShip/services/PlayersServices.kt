package DAW.BattleShip.services

import DAW.BattleShip.domain.*
import DAW.BattleShip.domain.ErrorHandling.LoginFailed
import DAW.BattleShip.domain.ErrorHandling.UnsafePassword
import DAW.BattleShip.domain.ErrorHandling.UsernameAlreadyExists
import DAW.BattleShip.http.model.*
import DAW.BattleShip.http.utils.Hashing
import DAW.BattleShip.repository.TransactionManager
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.List

@Component
class PlayersServices(
        private val transactionManager: TransactionManager,
        private val hash : Hashing
){

    private fun String.isSafe() : Boolean {
        val regex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}\$")
        return regex.matches(this)
    }

    fun createUser(creationPlayer: CreationPlayer) : UserCreationOutput{
        if(!creationPlayer.password.isSafe()) throw UnsafePassword()
        return transactionManager.run {
            val playersRepository = it.playersRepository

            if(playersRepository.userExists(creationPlayer.username)) throw UsernameAlreadyExists()

            val token = UUID.randomUUID().toString()

            val hashValue = hash.cypher(creationPlayer.password)

            val player = CreationPlayerDB(creationPlayer.username, hashValue,token = token )

            val id = playersRepository.storePlayer(player)

            UserCreationOutput(id,token)
        }
    }

    fun login(username: String, password: String) : UserCreationOutput{
        return transactionManager.run{
            val playersRepository = it.playersRepository
            val hashValue = hash.cypher(password)
            playersRepository.signIn(username,hashValue) ?: throw LoginFailed()
        }
    }

    fun rankings() : RankingOutput =
            transactionManager.run{
                RankingOutput(it.playersRepository.rankings())
            }


    fun playerInformation(user : Int) : PlayerOutput {
        return transactionManager.run {
            it.gamesRepository.infoPlayer(user)
        }
    }

    fun getPlayerByToken(token : String?) : Player?{
        if(token == null) return null
        return transactionManager.run {
            it.playersRepository.getPlayerByToken(token)
        }
    }

    fun getListGames(user : Int) : ListGamesOutput {
        return transactionManager.run {
            it.gamesRepository.listAllGames(user).toListGamesOutput(user)
        }
    }

    private fun List<Game>.toListGamesOutput(playerID : Int) : ListGamesOutput{
        return ListGamesOutput(
            this.map {
            it.toStateOutput(playerID)
        })
    }

    private fun Game.toStateOutput(playerID : Int) : StateOutput {
        return StateOutput(
            id = gameId,
            state =  toGameOutputState(playerID)
        )
    }

    private fun Game.toGameOutputState(id: Int) : GameOutputState {
        return when (state) {
            GameState.NOT_BEGUN -> GameOutputState.NOT_BEGUN
            GameState.PLAYER_A -> if (id == playerA) GameOutputState.YOUR_TURN else GameOutputState.OPPONENT_TURN
            GameState.PLAYER_B -> if (id == playerB) GameOutputState.YOUR_TURN else GameOutputState.OPPONENT_TURN
            GameState.PLAYER_A_WON -> if (id == playerA) GameOutputState.VICTORY else GameOutputState.DEFEAT
            GameState.PLAYER_B_WON -> if (id == playerB) GameOutputState.VICTORY else GameOutputState.DEFEAT
        }
    }



}