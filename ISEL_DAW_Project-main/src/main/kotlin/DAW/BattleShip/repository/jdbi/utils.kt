package DAW.BattleShip.repository.jdbi

import DAW.BattleShip.repository.jdbi.mappers.BoardMapper
import DAW.BattleShip.repository.jdbi.mappers.InstantMapper
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.postgres.PostgresPlugin

fun Jdbi.configure(): Jdbi {

    installPlugin(KotlinPlugin())
    installPlugin(PostgresPlugin())

    registerColumnMapper(BoardMapper())
    registerColumnMapper(InstantMapper())

    return this
}