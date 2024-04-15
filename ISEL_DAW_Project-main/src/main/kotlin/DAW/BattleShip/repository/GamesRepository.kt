package DAW.BattleShip.repository

import DAW.BattleShip.domain.Game
import DAW.BattleShip.http.model.PlayerOutput
import java.util.UUID

interface GamesRepository {
    fun create(game: Game)
    fun read(id : UUID) : Game?
    fun update(game: Game)
    fun infoPlayer(id : Int) : PlayerOutput
    fun isPlayerInThisGame(player : Int, gameID : UUID) : Boolean
    fun listAllGames(playerID : Int) : List<Game>
}