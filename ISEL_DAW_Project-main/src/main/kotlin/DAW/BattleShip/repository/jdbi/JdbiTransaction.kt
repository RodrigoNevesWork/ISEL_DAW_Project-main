package DAW.BattleShip.repository.jdbi

import DAW.BattleShip.repository.*
import org.jdbi.v3.core.Handle

class JdbiTransaction(
    private val handle: Handle
) : Transaction {

    override val playersRepository: PlayersRepository by lazy { JbdiPlayersRepository(handle) }

    override val gamesRepository: GamesRepository by lazy { JdbiGameRepository(handle) }

    override val lobbyRepository: LobbyRepository by lazy { JbdiLobbyRepository(handle) }

    override fun rollback() {
        handle.rollback()
    }
}