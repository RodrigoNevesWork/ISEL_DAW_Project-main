package DAW.BattleShip.domain

import DAW.BattleShip.domain.ErrorHandling.BadPlay
import BattleShip.API.domain.positions.Position
import BattleShip.API.domain.positions.toPosition
import DAW.BattleShip.http.model.ShipInput
import DAW.BattleShip.http.model.ShipOutput

abstract class Tile(val position: Position)

class MissShip(position: Position) : Tile(position)

abstract class StateShip(position: Position, val ship: Ship) : Tile(position)

fun StateShip.toShipOutput() : ShipOutput {
    val shipName = ship.shipType.type
    val state = toState(this)
    val position = position
    return ShipOutput(shipName,state,position)
}
fun toListOfShipOutput(list : List<StateShip>) : List<ShipOutput> =
     list.fold(listOf()){ total, item -> total + item.toShipOutput()}


class OwnShip(position: Position, ship: Ship) : StateShip(position,ship)

fun ShipInput.toOwnShip() : OwnShip{
    val ship = ship.toShipType(direction.toDirection())
    val position = position.toPosition()
    return OwnShip(position,ship)
}

fun toListOfOwnShip(list : List<ShipInput>) : List<OwnShip> =
     list.fold(listOf()){ total, item -> total + item.toOwnShip()}


class HitShip(position: Position,ship: Ship) : StateShip(position,ship)

class DownShip(position: Position, ship: Ship) : StateShip(position, ship)

enum class Direction(val char: Char) {
    H('H'), V('V');
}

fun String.toDirection() =
    if(this == "V" || this == "v" || this == "vertical" || this == "VERTICAL")  Direction.V else Direction.H


enum class State(val char: Char){
    WATER(' '),
    SHIP('#'),
    HIT('*'),
    DOWN('X'),
    MISS('O');
}

fun StateShip.change() =
    when(this){
        is OwnShip -> HitShip(position, ship)
        is HitShip -> DownShip(position, ship)
        else -> error("Cant Change State")
    }

fun toChar(ship: Tile?) =
    when (ship) {
        is OwnShip -> State.SHIP.char
        is HitShip -> State.HIT.char
        is DownShip -> State.DOWN.char
        is MissShip -> State.MISS.char
        else -> State.WATER.char
    }

fun toState(tile : Tile?) =
    when (tile) {
        is OwnShip -> State.SHIP
        is HitShip -> State.HIT
        is DownShip -> State.DOWN
        is MissShip -> State.MISS
        else -> State.WATER
    }


fun String.toTile(position: Position, shipName : String, direction: String) : Tile =
    when(this) {
        "#" -> OwnShip(position,shipName.toShipType(direction.toDirection()))
        "*" -> HitShip(position, shipName.toShipType(direction.toDirection()))
        "O" -> MissShip(position)
        "X" -> DownShip(position, shipName.toShipType(direction.toDirection()))
        else -> throw BadPlay()
    }

