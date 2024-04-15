package DAW.BattleShip.http.pipeline

import DAW.BattleShip.http.responseProblemJson
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [Exception::class])
    protected fun handleConflict( error : Exception) = error.responseProblemJson()
}