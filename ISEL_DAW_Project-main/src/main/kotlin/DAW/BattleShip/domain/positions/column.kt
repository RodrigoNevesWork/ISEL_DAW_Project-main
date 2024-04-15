package BattleShip.API.domain.positions

import DAW.BattleShip.domain.ErrorHandling.BadColumn

const val FIRST_LETTER = 'A'
const val MAX_SIZE = 26

data class Column(val letter: Char){
    val ordinal = letter -  FIRST_LETTER  + 1

    init {
        require(letter.uppercaseChar() in 'A'..'Z')
    }


}

fun Char.toColumnOrNull() : Column?{
    val char = this.uppercaseChar()
    return if(char in 'A'..'Z') Column(char) else null
}

fun Char.toColumn(): Column = toColumnOrNull() ?: throw BadColumn()

fun Int.toColumnOrNull() : Column?{
    val char = if(this in 1.. MAX_SIZE) FIRST_LETTER + this - 1 else return null
    return Column(char)
}

fun Int.toColumn() : Column = toColumnOrNull() ?: throw BadColumn()