package DAW.BattleShip.repository.jdbi.mappers

import DAW.BattleShip.domain.ErrorHandling.ErrorInMappingStringtoDataBase
import DAW.BattleShip.domain.Board
import DAW.BattleShip.domain.boardfromString
import org.jdbi.v3.core.mapper.ColumnMapper
import org.jdbi.v3.core.statement.StatementContext
import org.postgresql.util.PGobject
import java.sql.ResultSet

class BoardMapper : ColumnMapper<Board> {
    override fun map(r: ResultSet, columnNumber: Int, ctx: StatementContext?): Board {
            val string = r.getObject(columnNumber,PGobject::class.java)
            return boardfromString(string.value?: throw ErrorInMappingStringtoDataBase())
    }

}