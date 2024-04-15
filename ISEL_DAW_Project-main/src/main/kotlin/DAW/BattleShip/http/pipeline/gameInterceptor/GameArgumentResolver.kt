package DAW.BattleShip.http.pipeline.gameInterceptor

import DAW.BattleShip.domain.ErrorHandling.GameNotExists
import DAW.BattleShip.domain.ErrorHandling.Unauthorized
import DAW.BattleShip.domain.Game
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.servlet.http.HttpServletRequest

@Component
class GameArgumentResolver : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean = parameter.parameterType == Game::class.java

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val request = webRequest.getNativeRequest(HttpServletRequest::class.java)
            ?: throw Unauthorized()
        return getGameFrom(request)
    }

    companion object{
        private const val KEY = "GameArgumentResolver"

        fun addGameTo(game: Game, request: HttpServletRequest) {
            return request.setAttribute(KEY, game)
        }

        fun getGameFrom(request: HttpServletRequest): Game {
            val game = request.getAttribute(KEY)?.let {
                it as Game
            }
            return game?: throw GameNotExists()
        }
    }

}