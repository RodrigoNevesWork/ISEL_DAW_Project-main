package DAW.BattleShip.http.pipeline.playerPipeline

import DAW.BattleShip.domain.ErrorHandling.Unauthorized
import DAW.BattleShip.domain.Player
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.servlet.http.HttpServletRequest

@Component
class PlayerArgumentResolver : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean = parameter.parameterType == Player::class.java

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val request = webRequest.getNativeRequest(HttpServletRequest::class.java)
            ?: throw Unauthorized()
        return getPlayerFrom(request)
    }

    companion object{
        private const val KEY = "UserArgumentResolver"

        fun addPlayerTo(player: Player, request: HttpServletRequest) {
            return request.setAttribute(KEY, player)
        }

        fun getPlayerFrom(request: HttpServletRequest): Player {
            val player = request.getAttribute(KEY)?.let {
                it as Player
            }
            return player?: throw Unauthorized()
        }
    }

}