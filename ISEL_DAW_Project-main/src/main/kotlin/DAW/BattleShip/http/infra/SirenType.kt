package DAW.BattleShip.http.infra

import DAW.BattleShip.http.model.GameOutput
import DAW.BattleShip.domain.LobbyResult
import DAW.BattleShip.http.model.PartialGameOutput
import DAW.BattleShip.http.Rel
import DAW.BattleShip.http.Uris
import DAW.BattleShip.http.Uris.HOME
import DAW.BattleShip.http.model.*
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseCookie

import java.net.URI
import java.util.*

object InfoSiren{
    fun makeSiren(info: Info) : SirenModel<Info>{
        return siren(info){
            clazz("Info")
            action("signup", URI(Uris.Players.SIGNUP), HttpMethod.POST)
            action("login", URI(Uris.LOG_IN), HttpMethod.POST)
            action("About_Us",URI(Uris.ABOUT_US),HttpMethod.GET)
            link(Uris.ABOUT_US, Rel.getLink(Uris.ABOUT_US))
        }
    }

    // Home
    fun makeSiren(body: HomeOutput) =
        siren(body) {
            clazz("HomeOutput")
            action("signup", URI(Uris.Players.SIGNUP), HttpMethod.POST)
            action("login", URI(Uris.LOG_IN), HttpMethod.POST)
            action("About_Us",URI(Uris.ABOUT_US),HttpMethod.GET)
            link(HOME, Rel.getLink(HOME))
        }

}


object PlayerSiren {

    //Create User
    fun makeSiren(body: UserCreationOutput) =
        siren(body) {
            clazz("UserCreationOutput")
            action("User_Information",Uris.Players.byId(body.id), HttpMethod.GET)
            action("Start_Game",URI(Uris.Games.GAMES_HOME), HttpMethod.POST)
            action("Rankings", URI(Uris.Players.RANKING),HttpMethod.GET )
            action("Home",URI(HOME),HttpMethod.GET)
            action("About_Us",URI(Uris.ABOUT_US),HttpMethod.GET)
            link(Uris.Players.SIGNUP, Rel.getLink(Uris.Players.SIGNUP))
        }


    //Rankings
    fun makeSiren(body: RankingOutput) =
        siren(body) {
            clazz("RankingOutput")
            action("Start_Game",URI(Uris.Games.GAMES_HOME), HttpMethod.POST)
            action("Home",URI(HOME),HttpMethod.GET)
            action("About_Us",URI(Uris.ABOUT_US),HttpMethod.GET)
            link(Uris.Players.RANKING, Rel.getLink(Uris.Players.RANKING))
        }

    //Information of a Player
    fun makeSiren(id: Int, playerOutput: PlayerOutput) =
        siren(playerOutput) {
            clazz("PlayerOutput")
            action("Start_Game",URI(Uris.Games.GAMES_HOME), HttpMethod.POST)
            action("Rankings", URI(Uris.Players.RANKING),HttpMethod.GET )
            action("Home",URI(HOME),HttpMethod.GET)
            action("About_Us",URI(Uris.ABOUT_US),HttpMethod.GET)
            link(Uris.Games.GAMES_HOME, Rel.getLink(Uris.Players.GAMES))
            //  link(Uris.Games.GAME_BY_ID, Rel.getLink(Uris.Players.byId(id).toASCIIString()))
        }

    fun makeSiren(id: ResponseCookie) =
        siren(id) {
            clazz("Login")
            link(HOME, Rel.getLink(HOME))
            //  link(Uris.Games.GAME_BY_ID, Rel.getLink(Uris.Players.byId(id).toASCIIString()))
        }


    //Get list of games of a player
    fun makeSiren(body: ListGamesOutput) =
        siren(body) {
            clazz("ListGamesOutput")
            action("Start_Game", URI(Uris.Games.GAMES_HOME), HttpMethod.POST)
            action("Rankings", URI(Uris.Players.RANKING), HttpMethod.GET)
            action("Home", URI(HOME), HttpMethod.GET)
            action("About_Us", URI(Uris.ABOUT_US), HttpMethod.GET)
            link(Uris.Games.GAMES_HOME, Rel.getLink(Uris.Players.GAMES))
        }

}

object GameSiren{
    //Lobby
    fun makeSiren(body: LobbyResult): SirenModel<LobbyResult> {
        return siren(body) {
            clazz("LobbyResult")
            action("Rankings", URI(Uris.Players.RANKING),HttpMethod.GET )
            action("Home",URI(HOME),HttpMethod.GET)
            action("About_Us",URI(Uris.ABOUT_US),HttpMethod.GET)
            link(Uris.Games.LOBBY, Rel.SELF)
        }
    }



    fun makeSirenPlaceShips(id : UUID, gameOutput: GameOutput) : SirenModel<GameOutput>{
        return siren(gameOutput){
            clazz("GameOutput")
            action("Rankings", URI(Uris.Players.RANKING),HttpMethod.GET )
            action("Home",URI(HOME),HttpMethod.GET)
            action("About_Us",URI(Uris.ABOUT_US),HttpMethod.GET)
            action("Own_Ships", URI(Uris.Games.fleetByID(id).toASCIIString()), HttpMethod.GET)
            action("Opponent_Fleet", URI(Uris.Games.opponentFleetByID(id).toASCIIString()), HttpMethod.GET)
            link(Uris.Games.byId(id).toASCIIString(), Rel.SELF)
        }
    }

    fun makeSirenPlay(id : UUID, gameOutput : GameOutput) : SirenModel<GameOutput>{
        return siren(gameOutput){
            clazz("gameOutput")
            action("Rankings", URI(Uris.Players.RANKING),HttpMethod.GET )
            action("Home",URI(HOME),HttpMethod.GET)
            action("About_Us",URI(Uris.ABOUT_US),HttpMethod.GET)
            action("Own_Ships", URI(Uris.Games.fleetByID(id).toASCIIString()), HttpMethod.GET)
            action("Opponent_Fleet", URI(Uris.Games.opponentFleetByID(id).toASCIIString()), HttpMethod.GET)
            link(Uris.Games.byId(id).toASCIIString(), Rel.SELF)

        }
    }

    fun makeSirenOponnentFleet(output : PartialGameOutput, id : UUID) : SirenModel<PartialGameOutput>{
        return siren(output) {
            clazz("PartialGameOutput")
            action("Rankings", URI(Uris.Players.RANKING),HttpMethod.GET )
            action("Home",URI(HOME),HttpMethod.GET)
            action("About_Us",URI(Uris.ABOUT_US),HttpMethod.GET)
            action("Own_Ships", URI(Uris.Games.fleetByID(id).toASCIIString()), HttpMethod.GET)
            link(Uris.Games.byId(id).toASCIIString(), Rel.SELF)
        }
    }

    fun makeSirenOwnFleet(output : PartialGameOutput, id :UUID) : SirenModel<PartialGameOutput>{
        return siren(output) {
            clazz("PartialGameOutput")
            action("Rankings", URI(Uris.Players.RANKING),HttpMethod.GET )
            action("Home",URI(HOME),HttpMethod.GET)
            action("About_Us",URI(Uris.ABOUT_US),HttpMethod.GET)
            action("Opponent_Fleet", URI(Uris.Games.opponentFleetByID(id).toASCIIString()), HttpMethod.GET)
            link(Uris.Games.byId(id).toASCIIString(), Rel.SELF)
        }
    }

    fun makeSiren(go : GameOutput, id :UUID) : SirenModel<GameOutput>{
        return siren(go){
            action("Rankings", URI(Uris.Players.RANKING),HttpMethod.GET )
            action("Home",URI(HOME),HttpMethod.GET)
            action("About_Us",URI(Uris.ABOUT_US),HttpMethod.GET)
            action("Opponent_Fleet", URI(Uris.Games.opponentFleetByID(id).toASCIIString()), HttpMethod.GET)
            link(Uris.Games.byId(id).toASCIIString(), Rel.SELF)

        }
    }

    fun makeSiren() : SirenModel<Unit>{
        return siren(Unit){
            action("Start_Game", URI(Uris.Games.GAMES_HOME), HttpMethod.POST)
            action("Rankings", URI(Uris.Players.RANKING), HttpMethod.GET)
            action("Home", URI(HOME), HttpMethod.GET)
            action("About_Us", URI(Uris.ABOUT_US), HttpMethod.GET)
            link(Uris.Games.GAMES_HOME, Rel.getLink(Uris.Players.GAMES))

        }
    }


}