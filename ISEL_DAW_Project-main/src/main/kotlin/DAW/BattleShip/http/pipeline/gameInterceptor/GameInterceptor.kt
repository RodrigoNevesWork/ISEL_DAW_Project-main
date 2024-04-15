package DAW.BattleShip.http.pipeline.gameInterceptor

import DAW.BattleShip.domain.Game
import DAW.BattleShip.http.pipeline.playerPipeline.AuthorizationHeaderProcessor
import DAW.BattleShip.http.pipeline.playerPipeline.PlayerArgumentResolver
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class GameInterceptor(
    private val gameProcessor: GameProcessor,
    private val authorizationHeaderProcessor : AuthorizationHeaderProcessor
)  : HandlerInterceptor{
    private val NAME_AUTHORIZATION_HEADER = "Authorization"
    private val NAME_WWW_AUTHENTICATE_HEADER = "WWW-Authenticate"

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        if (handler is HandlerMethod && handler.methodParameters.any { it.parameterType == Game::class.java }) {
            val player = authorizationHeaderProcessor.process(request.getHeader(NAME_AUTHORIZATION_HEADER))
            val requestUri = request.requestURI

            val id = requestUri.substringAfter("games/").substringBefore("/")
                .also { println("---------------------$it----------------------") }

            val game = gameProcessor.process(id)

            return if (player == null) {

                response.status = 401
                response.addHeader(NAME_WWW_AUTHENTICATE_HEADER, AuthorizationHeaderProcessor.SCHEMA)
                response.writer.write("must be authenticated")
                response.writer.flush()

                false

            } else if (game == null) {
                response.status = 401
                response.writer.println("game does not exists")
                response.writer.flush()
                false
            } else if (game.playerA != player.id && game.playerB != player.id) {
                response.status = 404
                false
            } else {
                GameArgumentResolver.addGameTo(game, request)
                PlayerArgumentResolver.addPlayerTo(player, request)
                true
            }
        }
        return true
    }
}