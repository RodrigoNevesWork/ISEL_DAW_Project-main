package DAW.BattleShip.domain

import DAW.BattleShip.domain.ErrorHandling.ShipNotExists

enum class ShipType(val type : String, val size: Int){
    CARRIER("CARRIER", 5),
    BATTLESHIP("BATTLESHIP", 4),
    CRUISER("CRUISER", 3),
    SUBMARINE("SUBMARINE", 2),
    DESTROYER("DESTROYER", 1);

}

data class Ship(val shipType : ShipType, val direction: Direction)

fun String.toShipTypeOrNull(direction: Direction) : Ship? =
    when(this.uppercase()){
        "CARRIER" -> Ship(ShipType.CARRIER, direction)
        "CRUISER" -> Ship(ShipType.CRUISER, direction)
        "BATTLESHIP" -> Ship(ShipType.BATTLESHIP, direction)
        "DESTROYER" -> Ship(ShipType.DESTROYER, direction)
        "SUBMARINE" -> Ship(ShipType.SUBMARINE, direction)
        else -> null
    }

fun String.toShipType(direction: Direction) : Ship = toShipTypeOrNull(direction) ?: throw ShipNotExists()

fun String.toShip() : Ship{
    val split = this.split('-')
    val direction = split[1].toDirection()
    return split[0].toShipType(direction)
}