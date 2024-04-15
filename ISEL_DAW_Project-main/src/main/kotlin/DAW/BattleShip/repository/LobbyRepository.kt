package DAW.BattleShip.repository

import java.time.Instant
import java.util.*

interface LobbyRepository{
    fun insertPlayer(user_id : Int, board_size: Int, deadline: Instant?): Int
    fun removePlayer(user_id: Int,board_size: Int): Int
    fun checkLobby(playerID : Int,points : Double, board_size: Int, deadline: Instant?) : Int?
    fun checkIfEnterGame(playerID : Int) : UUID?
}