package DAW.BattleShip.repository.jdbi

import DAW.BattleShip.domain.Player
import DAW.BattleShip.repository.LobbyRepository
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo
import java.time.Instant
import java.util.*

class JbdiLobbyRepository(private val handle : Handle): LobbyRepository{

    override fun insertPlayer(user_id: Int, board_size: Int, deadline: Instant?): Int{
        return handle.createQuery(
            "insert into dbo.lobby(user_id, board_size,deadline ) values (:id,:board_size,:deadline) returning user_id".trimIndent())
            .bind("id", user_id)
            .bind("board_size",board_size)
            .bind("deadline",deadline)
            .mapTo<Int>()
            .first()
    }

    override fun removePlayer(user_id: Int, board_size: Int): Int {
        return handle.createQuery(
            "delete from dbo.lobby where user_id = (:id) and board_size = (:board_size) returning user_id".trimIndent())
            .bind("id", user_id)
            .bind("board_size", board_size)
            .mapTo<Int>()
            .first()
    }

    override fun checkLobby(playerID: Int, points: Double, board_size: Int, deadline: Instant?): Int? {
        val sql = if (deadline == null) "select * from (dbo.lobby join dbo.players on id = user_id)" +
                "where (points between :points - 500 and :points + 500) and (board_size = :size) and (deadline is null) and (id != :playerID)"
        else "select * from (dbo.lobby join dbo.players on id = :playerID)" +
                "where (points between :points - 500 and :points + 500) " +
                "and (board_size = :size) and (deadline = :deadline) and (id != :playerID)"
        val query = handle.createQuery(sql)
            .bind("playerID",playerID)
            .bind("size", board_size)
            .bind("points", points)
            .mapTo<Player>()
            .toList()
        if(query.isEmpty()){
            insertPlayer(playerID,board_size,deadline)
            return null
        }
        val id = query.first().id
        removePlayer(id, board_size)
        return id
    }

    /*
    *
    * substring(board, 1, charindex('|', board) - 1) = :size" */

    override fun checkIfEnterGame(playerID: Int): UUID? {
       val a = handle.createQuery(
            "select g.id from dbo.games g inner join dbo.players p on p.id = g.playerb " +
                    "where p.id=:playerID and g.state = 'NOT_BEGUN'"
    )
       .bind("playerID", playerID)
       .mapTo<UUID>()

        a.forEach { println(it) }
        return a.singleOrNull()
    }

}

