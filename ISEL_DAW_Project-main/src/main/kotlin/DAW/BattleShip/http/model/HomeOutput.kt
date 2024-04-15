package DAW.BattleShip.http.model

class HomeOutput(
    val title : String,
    val systemVersion : String,
    val authors : List<Author>
) {
    companion object{
        val getHome
            get() =
                HomeOutput(
                    title = "BattleShipAPI",
                    systemVersion = "1.0.1",
                    authors = getAuthors()
                )
    }

}