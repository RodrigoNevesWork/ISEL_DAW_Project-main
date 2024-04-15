package DAW.BattleShip.repository

import DAW.BattleShip.domain.*
import DAW.BattleShip.http.model.PlayerOutput
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo
import java.time.Instant
import java.util.*

class JdbiGameRepository(
    private val handle: Handle,
) : GamesRepository {

    override fun create(game: Game) {
        handle.createUpdate(
            """
           insert into dbo.games(id, playerA, playerB, state, boarda, boardb, created, updated, deadline) 
           values(:gameId,:playerA, :playerB, :state, :boardA, :boardB,  :created, :udpated, :deadLine)
           """.trimIndent()
        )
            .bind("gameId", game.gameId)
            .bind("playerA", game.playerA)
            .bind("playerB", game.playerB)
            .bind("boardA", game.boardA.toString())
            .bind("boardB", game.boardB.toString())
            .bind("state", game.state)
            .bind("created", game.created.epochSecond)
            .bind("udpated", game.updated.epochSecond)
            .bind("deadLine", game.deadLine?.epochSecond)
            .execute()
    }

    override fun read(id: UUID): Game? =
        handle.createQuery("select * from dbo.games where id = :id ")
            .bind("id", id)
            .mapTo<GameDbModel>()
            .singleOrNull()?.run {
                toGame()
            }


    override fun infoPlayer(id: Int): PlayerOutput {
        val a = handle.createQuery(
           "select p.id,p.username, p.points , count(p.id) as nrOfGames " +
                   "from dbo.players p inner join dbo.games g on p.id = g.playera or p.id = g.playerb " +
                   "where p.id = :id " +
                   "group by p.id, p.username, p.points " +
                   "order by p.points"
       )
            .bind("id", id)
            .mapTo<PlayerOutput>()
            .single()
        print(a)
        return a
    }


    override fun isPlayerInThisGame(player: Int, gameID: UUID): Boolean =
        handle.createQuery("select * from dbo.games where id = :gameID and (playera = :playera or playerb = :playerb)")
            .bind("gameID",gameID)
            .bind("playera",player)
            .bind("playerb",player)
            .mapTo<Int>()
            .single() == 1

    override fun listAllGames(playerID: Int): List<Game> {
       return  handle.createQuery("select * from dbo.games where playera = :id or playerb = :id ")
            .bind("id", playerID)
            .mapTo<GameDbModel>().map {
                it.toGame()
            }
           .toList()
}


    override fun update(game: Game) {
        handle.createUpdate(
              "update dbo.games set boarda = :boardA,boardb = :boardB,updated = :updated,state = :state where id = :id ".trimIndent())
            .bind("boardA", game.boardA.toString())
            .bind("boardB", game.boardB.toString())
            .bind("updated", game.updated.epochSecond)
            .bind("state", game.state)
            .bind("id", game.gameId)
            .execute()
    }

}
class GameDbModel(
    private val id: UUID,
    private val playerA: Int,
    private val playerB: Int,
    private val state: GameState,
    private val boardA: String,
    private val boardB: String,
    private val created: Instant,
    private val updated: Instant,
    private val deadLine : Instant?
) {
    fun toGame() = Game(
        playerA,
        playerB,
        boardfromString(boardA),
        boardfromString(boardB),
        state,
        id,
        created,
        updated,
        deadLine
    )
}