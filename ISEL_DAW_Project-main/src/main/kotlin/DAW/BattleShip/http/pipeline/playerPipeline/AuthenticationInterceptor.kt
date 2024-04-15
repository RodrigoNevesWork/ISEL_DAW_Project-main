package DAW.BattleShip.http.pipeline.playerPipeline

import DAW.BattleShip.domain.Player
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationInterceptor(
    private val authorizationHeaderProcessor : AuthorizationHeaderProcessor
)  : HandlerInterceptor{
    private val NAME_AUTHORIZATION_HEADER = "Authorization"
    private val NAME_WWW_AUTHENTICATE_HEADER = "WWW-Authenticate"

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean{
        if(handler is HandlerMethod && handler.methodParameters.any{ it.parameterType == Player::class.java }){
            val player = authorizationHeaderProcessor.process(request.getHeader(NAME_AUTHORIZATION_HEADER))
            return if(player == null){
                response.status = 401
                response.addHeader(NAME_WWW_AUTHENTICATE_HEADER, AuthorizationHeaderProcessor.SCHEMA)
                false
            }else{
                PlayerArgumentResolver.addPlayerTo(player,request)
                true
            }

        }
        return true
    }
}