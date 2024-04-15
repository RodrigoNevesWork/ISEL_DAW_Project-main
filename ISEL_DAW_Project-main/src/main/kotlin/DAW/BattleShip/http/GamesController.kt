package DAW.BattleShip.http

import BattleShip.API.domain.positions.toPosition
import DAW.BattleShip.domain.*
import DAW.BattleShip.http.infra.GameSiren
import DAW.BattleShip.http.infra.SirenModel
import DAW.BattleShip.services.GamesServices
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import DAW.BattleShip.http.infra.response
import DAW.BattleShip.http.infra.responseRedirect
import DAW.BattleShip.http.model.*
import java.util.*

@RestController
class GamesController(
    private val gamesServices: GamesServices
){

    @PostMapping(Uris.Games.GAMES_HOME)
    fun create(@RequestBody input: GameCreation, player : Player)
    : ResponseEntity<SirenModel<LobbyResult>> {

            val newGame = gamesServices.startNewGame(player.id,player.points,input.board_size)
            val siren = GameSiren.makeSiren(newGame)
            return if(newGame.id == null) {
                siren.responseRedirect(200, Uris.Games.LOBBY)
            }else{
                siren.responseRedirect(201, Uris.Games.placingPhaseById(newGame.id).toASCIIString())
            }
    }

    @PostMapping(Uris.Games.PLACING_PHASE)
    fun placeShips(
        @RequestBody input: List<ShipInput>, player: Player, game: Game
    ) : ResponseEntity<SirenModel<GameOutput>>{
            val ships = toListOfOwnShip(input)
            val gameOutput = gamesServices.placeShips(player.id, ships, game)
            return GameSiren.makeSirenPlaceShips(game.gameId, gameOutput).response(200)
    }

    @PostMapping(Uris.Games.GAME_BY_ID)
    fun makePlay(
        @RequestBody input: PositionInput, player : Player,game: Game
    ) : ResponseEntity<SirenModel<GameOutput>>{
           val gameOutput = gamesServices.shot(player.id, game,input.position.toPosition())
           return GameSiren.makeSirenPlay(game.gameId,gameOutput).response(200)
    }


    @GetMapping(Uris.Games.GAME_BY_ID)
    fun getGame(
        player: Player,game: Game
    ) : ResponseEntity<SirenModel<GameOutput>> {
        val gameOutput = gamesServices.checkGame(player.id,game)
        return GameSiren.makeSiren(gameOutput,game.gameId).response(200)

    }
    @GetMapping(Uris.Games.OPPONENT_FLEET)
    fun checkOpponentFleet(
        player : Player, game: Game
    ) : ResponseEntity<SirenModel<PartialGameOutput>> {
            val fleet = gamesServices.checkOpponentFleet(player.id, game)
           return GameSiren.makeSirenOponnentFleet(fleet,game.gameId).response(200)

    }

    @GetMapping(Uris.Games.OWN_FLEET)
    fun checkOwnFleet(
        player : Player, game: Game
    ) : ResponseEntity<SirenModel<PartialGameOutput>> {
            val fleet = gamesServices.checkOwnFleet(player.id, game)
           return GameSiren.makeSirenOwnFleet(fleet,game.gameId).response(200)
    }

    @GetMapping(Uris.Games.LOBBY)
    fun checkLobby(@RequestParam player : Int) : ResponseEntity<SirenModel<LobbyResult>>{
        val possibleGame = gamesServices.checkLobby(player)
        return GameSiren.makeSiren(LobbyResult(possibleGame)).response(200)
    }

    @PostMapping(Uris.Games.QUIT_GAME)
    fun quitGame(game: Game, player: Player) : ResponseEntity<SirenModel<Unit>> {
        gamesServices.quitGame(player.id,game)
        return GameSiren.makeSiren().response(204)

    }
}