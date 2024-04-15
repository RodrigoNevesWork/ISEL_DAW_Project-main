package DAW.BattleShip.repository

interface Transaction {

    val playersRepository: PlayersRepository

    val gamesRepository: GamesRepository

    val lobbyRepository: LobbyRepository

    fun rollback()
}