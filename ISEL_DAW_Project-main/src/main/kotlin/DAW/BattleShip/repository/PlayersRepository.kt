package DAW.BattleShip.repository

import DAW.BattleShip.domain.CreationPlayerDB
import DAW.BattleShip.domain.Player
import DAW.BattleShip.http.model.PlayerOutput
import DAW.BattleShip.http.model.UserCreationOutput

interface PlayersRepository {
    fun getPlayerById(id :Int) : Player?
    fun getPlayerByToken(token : String) : Player?
    fun storePlayer(player : CreationPlayerDB) : Int
    fun userExists(username : String) : Boolean
    fun signIn(username: String, pass : String) : UserCreationOutput?
    fun updatePoints(id : Int, points : Double )
    fun rankings() : List<PlayerOutput>
}