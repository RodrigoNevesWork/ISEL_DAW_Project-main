package DAW.BattleShip.http

import org.springframework.web.util.UriTemplate
import java.net.URI
import java.util.*

object Uris {

    const val CHECK_SESSION = "/check_session"
    const val PATH = "http://localhost:8080"
    const val ABOUT_US = "/about_us"
    const val LOG_IN = "/login"
    const val LOG_OUT = "/logout"
    const val HOME = "/"

    object Players{
        const val SIGNUP = "/players"

        const val RANKING = "/ranking"
        const val GET_BY_ID = "/players/{id}"
        const val GAMES = "/players/{id}/games"
        fun byId(id: Int) = UriTemplate(GET_BY_ID).expand(id.toString())
        fun home(): URI = URI(HOME)

    }

    object Games{
        const val GAME_BY_ID = "/games/{id}"
        const val QUIT_GAME = "/games/{id}/quit"
        const val GAMES_HOME = "/games"
        const val PLACING_PHASE = "/games/{id}/placing_phase"
        const val OPPONENT_FLEET = "/games/{id}/opponent"
        const val OWN_FLEET = "/games/{id}/ownfleet"
        const val LOBBY = "/games/lobby"


        fun byId(id: UUID) = UriTemplate(GAME_BY_ID).expand(id.toString())
        fun placingPhaseById(id : UUID) = UriTemplate(PLACING_PHASE).expand(id.toString())
        fun fleetByID(id : UUID) = UriTemplate(OWN_FLEET).expand(id.toString())
        fun opponentFleetByID(id : UUID) = UriTemplate(OPPONENT_FLEET).expand(id.toString())

    }
}