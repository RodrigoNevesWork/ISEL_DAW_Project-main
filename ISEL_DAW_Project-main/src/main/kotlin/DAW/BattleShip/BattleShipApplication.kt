package DAW.BattleShip

import DAW.BattleShip.domain.Clock
import DAW.BattleShip.http.utils.Hashing
import DAW.BattleShip.repository.jdbi.configure
import org.jdbi.v3.core.Jdbi
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.time.Instant

@SpringBootApplication
class BattleShipApplication {
    @Bean
    fun jdbi() = Jdbi.create(
        PGSimpleDataSource().apply {
            setURL(System.getenv("DATABASE_DAW"))
        }

    ).configure()

    @Bean
    fun clock() = object : Clock {
        override fun now() = Instant.now()
    }

    @Bean
    fun hash() = Hashing()
}




fun main() {
    runApplication<BattleShipApplication>()
}

