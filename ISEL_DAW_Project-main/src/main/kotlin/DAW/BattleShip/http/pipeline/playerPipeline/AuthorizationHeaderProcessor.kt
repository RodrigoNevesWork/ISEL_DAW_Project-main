package DAW.BattleShip.http.pipeline.playerPipeline

import DAW.BattleShip.domain.Game
import DAW.BattleShip.domain.Player
import DAW.BattleShip.services.GamesServices
import DAW.BattleShip.services.PlayersServices
import org.springframework.stereotype.Component
import java.util.*

@Component
class AuthorizationHeaderProcessor(
    val playersServices: PlayersServices,
) {

    fun process(credentials : String?) : Player?{
        if(credentials == null) return null

        val parts = credentials.trim().split(" ")

        if(parts.size != 2 || parts[0].lowercase() != SCHEMA) return null

        return playersServices.getPlayerByToken(parts[1])
    }
    companion object{
        const val SCHEMA = "bearer"
    }







}