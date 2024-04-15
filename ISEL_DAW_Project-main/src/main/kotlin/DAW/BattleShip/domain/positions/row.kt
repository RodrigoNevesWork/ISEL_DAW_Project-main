package BattleShip.API.domain.positions

import DAW.BattleShip.domain.ErrorHandling.BadRow

val RANGE = 1..26

data class Row(val number : Int){
    val ordinal = number
    init {
        require(number in RANGE)
    }
}

fun String.toRowOrNull() : Row?{
    val num = toIntOrNull() ?: return null
    if(num !in RANGE) return null
    return Row(num)
}

fun String.toRow() = toRowOrNull() ?: throw BadRow()

fun Int.toRowOrNull() : Row?{
    return if(this in RANGE)  Row(this) else null
}

fun Int.toRow() = toRowOrNull() ?: throw BadRow()
