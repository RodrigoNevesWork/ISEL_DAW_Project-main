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
import kotlin.test.assertEquals


class PlayersRepositoryTest{
/*

	fun jdbi() = Jdbi.create(
		PGSimpleDataSource().apply {
			setURL("jdbc:postgresql://localhost/postgres?user=postgres&password=lsverao")
		}
	).configure()

	@Test
	fun testCreatePlayer(){

		val transactionManager = JdbiTransactionManager(jdbi())
		val players = PlayersServices(transactionManager)
		val creationB = CreationPlayer("Mafalda", "Paaasss%47184")
		val player = players.createUser(creationB)
		val token = players.logIn("Mafalda", "Paaasss%47184")
		assertEquals(player.second, token)

	}

	@Test
	fun playerRankingsTest(){
		val transactionManager = JdbiTransactionManager(jdbi())
		val players = PlayersServices(transactionManager)
		val creationA = CreationPlayer("Mafalda", "47184@Mafalda")
		val creationB = CreationPlayer("Ines", "47188@Ines")
		val creationC = CreationPlayer("Rodrigo", "46256@Rodrigo")
		val a = players.createUser(creationA)
		val b = players.createUser(creationB)
		val c = players.createUser(creationC)
		val rankings = players.rankings()
		players.updatePoints(a.first, 2000.0)
		assertEquals(rankings.size, 3)
		assertEquals(rankings[0].id, a.first)
	}

	@Test
	fun numberOfGamesTest(){
		val transactionManager = JdbiTransactionManager(jdbi())
		val players = PlayersServices(transactionManager)
		val games = GamesServices(transactionManager, RealClock)
		val creationA = CreationPlayer("test", "1234@teAst")
		val creationB = CreationPlayer("test1","4321@tesAt")
		val a = players.createUser(creationA)
		val b = players.createUser(creationB)
		val number = players.numberOfGames(a.first)
		assertEquals(number,0)
		games.startNewGame(a.second, 40, Instant.MAX)
		val number1 = players.numberOfGames(a.first)
		assertEquals(number1,1)
	}

 */

}