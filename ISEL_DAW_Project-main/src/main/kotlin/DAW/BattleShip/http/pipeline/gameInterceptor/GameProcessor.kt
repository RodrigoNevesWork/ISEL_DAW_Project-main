package DAW.BattleShip.http.pipeline.gameInterceptor

import DAW.BattleShip.domain.Game
import DAW.BattleShip.services.GamesServices
import org.springframework.stereotype.Component
import java.util.*

@Component
class GameProcessor(
    private val gamesServices: GamesServices
) {
    fun process(id : String) : Game?{
        val gameID = try{
            UUID.fromString(id)
        }catch (_ : IllegalArgumentException ){
            return null
        }
        return gamesServices.getGame(gameID)

    }
}