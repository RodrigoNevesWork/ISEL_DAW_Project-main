package DAW.BattleShip.domain

import BattleShip.API.domain.positions.Position
import BattleShip.API.domain.positions.shipPositions
import BattleShip.API.domain.positions.toPosition
import DAW.BattleShip.domain.ErrorHandling.*

const val MINIMUM_SHIP = 3
data class Board(val grid : List<Tile>, val  size : Int){

   override fun toString() : String{
       val str = java.lang.StringBuilder("$size|")
       for(i in grid){
           val ship = if(i is StateShip) ",${i.ship.shipType},${i.ship.direction}" else ",NOSHIP,DIRECTION"
           str.append( when (i) {
               is OwnShip -> "#,${i.position}$ship|"
               is MissShip -> "O,${i.position}$ship|"
               is DownShip -> "X,${i.position}$ship|"
               is HitShip  -> "*,${i.position}$ship|"
               else -> throw BadPlay()
           })
       }
        return str.dropLast(1).toString()
    }
}


fun boardfromString(str : String) : Board{
    val strSplit  = str.split('|')
    val size = strSplit[0].toInt()
    if(strSplit.size == 1) return Board(emptyList(),size)
    val tiles = mutableListOf<Tile>()
    for(i in 1 until strSplit.size){
        val split = strSplit[i].split(',')
        val state = split[0]
        val pos = split[1]
        val ship = split[2]
        val direction = split[3]
        val tile = state.toTile(pos.toPosition(),ship,direction)
        tiles.add(tile)
    }

return Board(tiles,size)
}

fun List<Tile>.canPlace(ship: Ship, pos: Position): Boolean {
    return this.positionsAvailable(pos, ship.direction, ship.shipType.size)
}


fun List<Tile>.placeOnGrid(ship: Ship, position: Position, size: Int) : List<Tile> {
    val list = position.shipPositions(ship.direction, ship.shipType.size).map {
        if(size < it.column.ordinal ) throw BadColumn()
        if(size < it.row.ordinal) throw BadRow()
        OwnShip(it, ship)
    }
    return this + list
}

fun Board.shot(position: Position): Board {
    val finalGrid = shotOnGrid(position)
    val a = finalGrid.sinkShip(position)
    a.grid.forEach{
        if(it is StateShip) println(" position -> $it.position || state ->${it.javaClass}")
    }
    return copy(grid = finalGrid.sinkShip(position).grid)
}

fun Board.play(pos: Position, player: Int, turn: Int) =
    if (player == turn) shot(pos) else throw WrongTurn()


fun Board.lost() : Boolean = grid.none { it is OwnShip }

fun Board.isMissShot(position: Position) = getState(position) == State.MISS.char

fun Board.getState(position: Position): Char = toChar(getTileOrNull(position))

fun Board.getTileOrNull(position: Position) =
    this.grid.find { it.position.column == position.column && it.position.row == position.row }

fun Board.getTile(position: Position): Tile = getTileOrNull(position) ?: throw BadPosition()

fun List<Tile>.positionsAvailable(position: Position, direction: Direction, squares: Int): Boolean {
    if((position.column.ordinal+squares >= size && direction == Direction.H)
        || (position.row.number+squares >= size && direction == Direction.V)) return false
    val ship = position.shipPositions(direction, squares)
    this.forEach {
        if (it is OwnShip) {
            ship.forEach { square ->
                if (square.isAdjacent(it.position)) return false
            }
        }
    }
    return true
}

fun Board.isShipSunk(ship: Ship): Boolean {
    return grid.filterIsInstance<HitShip>().filter { it.ship === ship }.size == ship.shipType.size
}

fun Board.sinkShip(position: Position): Board {
    val tile = getTile(position)
    if (tile is StateShip)
        if (isShipSunk(tile.ship)){
            return Board(grid.map {
                if (it is StateShip && it.ship == tile.ship)
                    DownShip(it.position, it.ship)
                else
                    it
            }, size)
        }
    return Board(grid,size)
}

fun Board.shotOnGrid(position: Position) : Board {
    val tile = getTileOrNull(position)
    return when (toChar(tile)) {
        State.WATER.char -> Board(grid + MissShip(position), size)
        State.SHIP.char -> Board(if (tile is OwnShip) (grid - tile) + tile.change() else grid,size).sinkShip(position)
        State.MISS.char -> this
        State.DOWN.char -> this
        State.HIT.char -> this
        else -> throw BadPlay()
    }
}

private fun checkFleet(ships: List<OwnShip>) {
    if(
        ships.count{ it.ship.shipType == ShipType.CRUISER } != MINIMUM_SHIP ||
        ships.count{ it.ship.shipType == ShipType.DESTROYER } != MINIMUM_SHIP ||
        ships.count{ it.ship.shipType == ShipType.CARRIER } != MINIMUM_SHIP ||
        ships.count{ it.ship.shipType == ShipType.SUBMARINE } != MINIMUM_SHIP ||
        ships.count{ it.ship.shipType == ShipType.BATTLESHIP} != MINIMUM_SHIP
            ) throw BadFleet()
}

fun Board.placeAllShips(ships : List<OwnShip>) : Board {
   // checkFleet(ships)
    return Board(ships.fold( listOf()){  total, item -> total.placeOnGrid(item.ship,item.position,size) }, size)
}


