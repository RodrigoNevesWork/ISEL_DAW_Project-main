package DAW.BattleShip.http

import DAW.BattleShip.domain.ErrorHandling.Errors
import org.springframework.http.ResponseEntity
import java.net.URI

const val PATH = "http://localhost:8080"


data class ProblemJson(
    val type: URI,
    val title: String,
    val status: Int
) {
    companion object{
        const val MEDIA_TYPE = "application/problem+json"
    }

    fun response() : ResponseEntity<*> =
        ResponseEntity
            .status(status)
            .header("Content-Type", MEDIA_TYPE)
            .body(this)

}

private fun getURI(exception: Exception): URI {
    val uri = if(exception is Errors)
                 exception.message.replace(" ", "-").lowercase()
             else
                 "internal_error"


    return URI("${PATH}/$uri")
}

private fun Exception.toProblemJson() : ProblemJson{
    val title = if(this is Errors) message else this.message ?: "Unknow Error"
    val status = if(this is Errors) httpStatus else 500

    return ProblemJson(
        type = getURI(this),
        title = title,
        status = status
    )
}


fun Exception.responseProblemJson() :  ResponseEntity<*> = toProblemJson().response()

