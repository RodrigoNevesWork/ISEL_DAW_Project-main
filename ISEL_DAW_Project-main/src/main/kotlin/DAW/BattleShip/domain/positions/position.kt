package BattleShip.API.domain.positions

import DAW.BattleShip.domain.ErrorHandling.BadPosition
import DAW.BattleShip.domain.Direction

data class Position(val column : Column, val row : Row){
    override fun toString(): String = "${column.letter}${row.number}"

    fun isAdjacent(position: Position) : Boolean {
        return (this.column.ordinal) - (position.column.ordinal) in -1..1 &&
                (this.row.ordinal) - (position.row.ordinal) in -1..1
    }

}

fun String.toPositionOrNull() : Position?{
    val column = this[0].toColumnOrNull() ?: return null

    val rowString = if(this.length > 2) this[1].toString() + this[2] else this[1].toString()
    val row = rowString.toRowOrNull() ?: return null

    return Position(column,row)
}

fun String.toPosition() = toPositionOrNull() ?: throw BadPosition()

fun Position.shipPositions(direction: Direction, squares: Int) =
    List(squares) {
        if (direction === Direction.H)
            Position((column.letter + it).toColumn(), row)
        else
            Position(column, (row.number + it).toRow())
    }
