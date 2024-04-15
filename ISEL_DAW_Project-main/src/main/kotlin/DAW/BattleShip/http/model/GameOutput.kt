package DAW.BattleShip.http.model

import BattleShip.API.domain.positions.Position
import DAW.BattleShip.domain.State
import DAW.BattleShip.domain.Tile
import java.util.*

enum class GameOutputState{
    NOT_BEGUN,
    YOUR_TURN,
    OPPONENT_TURN,
    VICTORY,
    DEFEAT
}

data class ShipOutput(val name : String, val state : State, val position : Position)

data class OutputShip(val ship : Tile, val state : String)

data class PartialGameOutput(val board : List<OutputShip>, val state : GameOutputState, val size : Int)

data class GameOutput(val ownBoard : List<OutputShip>, val enemyBoard : List<OutputShip>, val state : GameOutputState, val size : Int)

data class StateOutput(val id: UUID, val state: GameOutputState)
