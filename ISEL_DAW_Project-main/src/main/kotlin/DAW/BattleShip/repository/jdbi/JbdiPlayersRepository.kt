package DAW.BattleShip.repository.jdbi

import DAW.BattleShip.domain.CreationPlayerDB
import DAW.BattleShip.domain.Player
import DAW.BattleShip.http.model.PlayerOutput
import DAW.BattleShip.http.model.UserCreationOutput
import DAW.BattleShip.repository.PlayersRepository
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo

class JbdiPlayersRepository(
        private val handle : Handle
) : PlayersRepository {
    private val POINTS = 1500.0

    override fun getPlayerById(id: Int): Player? =
            handle.createQuery("select * from dbo.players where id = :id")
                    .bind("id", id)
                    .mapTo<Player>()
                    .singleOrNull()

    override fun getPlayerByToken(token: String): Player? =
            handle.createQuery("select * from dbo.players where token = :token")
                    .bind("token", token)
                    .mapTo<Player>()
                    .singleOrNull()


    override fun storePlayer(player: CreationPlayerDB): Int {
        return handle.createQuery(
                "insert into dbo.players( username, password, points, token) values (:username,:password,:points,:token) returning id".trimIndent()
        )
                .bind("username", player.username)
                .bind("password", player.password)
                .bind("points", POINTS)
                .bind("token", player.token)
                .mapTo<Int>()
                .single()
    }

    override fun userExists(username: String): Boolean {
        return handle.createQuery("select count(*) from dbo.players where username = :username")
                .bind("username", username)
                .mapTo<Int>()
                .single() == 1

    }

    override fun signIn(username: String, pass: String): UserCreationOutput? =
            handle.createQuery("select id, token from dbo.players where username = :username and password = :pass")
                    .bind("username", username)
                    .bind("pass", pass)
                    .mapTo<UserCreationOutput>()
                    .singleOrNull()

    override fun updatePoints(id: Int, points: Double) {
        handle.createUpdate("update dbo.players set points = :points where id = :id")
                .bind("points", points)
                .bind("id", id)
                .execute()
    }

    override fun rankings() = handle.createQuery(
            "select p.id, p.username, p.points , count(p.id) as nrOfGames " +
                    "from dbo.players p left join dbo.games g on p.id = g.playera or p.id = g.playerb " +
                    "group by p.id, p.username, p.points " +
                    "order by p.points DESC"
    )
            .mapTo<PlayerOutput>()
            .toList()
}