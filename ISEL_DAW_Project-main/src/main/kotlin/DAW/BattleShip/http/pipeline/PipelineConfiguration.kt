package DAW.BattleShip.http.pipeline

import DAW.BattleShip.http.pipeline.gameInterceptor.GameArgumentResolver
import DAW.BattleShip.http.pipeline.gameInterceptor.GameInterceptor
import DAW.BattleShip.http.pipeline.playerPipeline.AuthenticationInterceptor
import DAW.BattleShip.http.pipeline.playerPipeline.PlayerArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class PipelineConfigurer(
    val gameInterceptor: GameInterceptor,
    val gameArgumentResolver : GameArgumentResolver,
    val authenticationInterceptor: AuthenticationInterceptor,
    val PlayerArgumentResolver: PlayerArgumentResolver
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authenticationInterceptor)
        registry.addInterceptor(gameInterceptor)
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(PlayerArgumentResolver)
        resolvers.add(gameArgumentResolver)
    }
}