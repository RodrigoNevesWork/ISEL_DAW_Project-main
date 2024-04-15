package DAW.BattleShip.http

import DAW.BattleShip.domain.CreationPlayer
import DAW.BattleShip.domain.Player
import DAW.BattleShip.http.infra.PlayerSiren.makeSiren
import DAW.BattleShip.http.infra.SirenModel
import DAW.BattleShip.http.model.PlayerOutput
import DAW.BattleShip.http.model.RankingOutput
import DAW.BattleShip.http.model.UserCreationOutput
import DAW.BattleShip.services.PlayersServices
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import DAW.BattleShip.http.infra.response
import DAW.BattleShip.http.infra.responseRedirect
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
class PlayersController(
        private val playersServices: PlayersServices,
){
    @PostMapping(Uris.Players.SIGNUP)
    fun create(@RequestBody input : CreationPlayer ) : ResponseEntity<SirenModel<UserCreationOutput>>{
        val res = playersServices.createUser(input)
        return makeSiren(res).responseRedirect(201,Uris.Players.byId(res.id).toASCIIString())
    }

    @GetMapping(Uris.Players.GET_BY_ID)
    fun infoUser(@PathVariable id : Int ): ResponseEntity<SirenModel<PlayerOutput>>{
        return makeSiren(id,playersServices.playerInformation(id)).response(200)
    }


    @PostMapping(Uris.LOG_IN)
    fun login(@RequestBody input: CreationPlayer,
              response: HttpServletResponse
    ) : ResponseEntity<SirenModel<UserCreationOutput>> {

        val user = playersServices.login(input.username, input.password) // gets the players token

        val cookieToken = ResponseCookie
            .from("token", user.token)
            .path("/")
            .maxAge(7 * 24 * 60 * 60 )
            .httpOnly(true)
            .secure(false)
            .build()

        val cookieId =  ResponseCookie
            .from("id", user.id.toString())
            .path("/")
            .maxAge(7 * 24 * 60 * 60 )
            .httpOnly(true)
            .secure(false)
            .build()

        response.addHeader(HttpHeaders.SET_COOKIE, cookieToken.toString())
        response.addHeader(HttpHeaders.SET_COOKIE, cookieId.toString())

        return makeSiren(user).response(200)
    }

    @PostMapping(Uris.LOG_OUT)
    fun logout(response: HttpServletResponse){
        //removes the token of the cookie to logout
        val cookieToken = Cookie("token", null)
        cookieToken.maxAge = 0
        cookieToken.secure = false
        cookieToken.path = "/"
        cookieToken.isHttpOnly = true

        val cookieId = Cookie("token", null)
        cookieId.maxAge = 0
        cookieId.secure = false
        cookieId.path = "/"
        cookieId.isHttpOnly = true

        response.addCookie(cookieToken)
        response.addCookie(cookieId)

    }

    @GetMapping(Uris.CHECK_SESSION)
    fun check(
        request: HttpServletRequest
    ) : Array<out Cookie>? {
        val cookies = request.cookies
        println("check cookies $cookies")
        return cookies
    }


    @GetMapping(Uris.Players.RANKING)
    fun ranking() : ResponseEntity<SirenModel<RankingOutput>> = makeSiren(playersServices.rankings()).response(200)

    @GetMapping(Uris.Players.GAMES)
    fun listGames( @PathVariable id: Int) = makeSiren(playersServices.getListGames(id)).response(200)

}