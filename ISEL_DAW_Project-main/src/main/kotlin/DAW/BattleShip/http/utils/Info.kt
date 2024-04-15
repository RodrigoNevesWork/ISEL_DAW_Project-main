package DAW.BattleShip.http.model

data class Info(
     val systemVersion: String,
     val authors: List<Author>
)

data class Author(
    val name: String,
    val email: String
)

fun getAuthors() = listOf(
    Author("Rodrigo Neves", "46536@alunos.isel.pt"),
    Author("Mafalda Rodrigues","47184@alunos.isel.pt"),
    Author("Inês Martins", "47188@alunos.isel.pt")
    )

fun getInformation() =
    Info(
        "1.1",
        getAuthors()
    )