package DAW.BattleShip.Services

import DAW.BattleShip.domain.CreationPlayer
import DAW.BattleShip.domain.RealClock
import DAW.BattleShip.repository.jdbi.JdbiTransactionManager
import DAW.BattleShip.repository.jdbi.configure
import DAW.BattleShip.services.GamesServices
import DAW.BattleShip.services.PlayersServices
import org.jdbi.v3.core.Jdbi
import org.junit.jupiter.api.Test
import org.postgresql.ds.PGSimpleDataSource
import java.time.Instant
import kotlin.test.assertNotNull


class GamesServicesTest{
/*
    fun jdbi() = Jdbi.create(
        PGSimpleDataSource().apply {
            setURL("jdbc:postgresql://localhost/postgres?user=postgres&password=lsverao")
        }
    ).configure()


    @Test
    fun createGameTest(){
        val transactionManager = JdbiTransactionManager(jdbi())
        val playersServices = PlayersServices(transactionManager)
        val gamesServices = GamesServices(transactionManager, RealClock)
        val player1 = playersServices.createUser(CreationPlayer("player1", "playerAa1@"))
        gamesServices.startNewGame(player1.second, 40, Instant.MAX)
        val player2 = playersServices.createUser(CreationPlayer("player2", "playerAa2@"))
        val id = gamesServices.startNewGame(player2.second, 40, Instant.MAX)
        assertNotNull(id)
    }


 */


}