package DAW.BattleShip.domain.ErrorHandling

abstract class Errors(val httpStatus : Int, override val message : String) : Exception()

class BadColumn : Errors(400,"Column not found")

class BadRow : Errors( 400,"Row not found")

class BadPosition : Errors(400,"Bad Position")

class ShipNotExists : Errors(400,"Ship doesn't exists")

class WrongTurn : Errors(400,"Wrong turn")

class BadPlay : Errors(404,"Bad play")

class BadFleet : Errors(404, "Must have 3 ships of each type")

class GameAlreadyOver : Errors(400,"Game is already over")

class ErrorInMappingStringtoDataBase : Errors(500,"Error mapping string to database")

class UnsafePassword : Errors(406,"Unsafe password")

class UsernameAlreadyExists : Errors(400,"User already exists")

class LoginFailed : Errors(401,"Login failed")

class UserNotExists : Errors(404,"User doesn't exists")

class GameNotExists : Errors(404,"Game doesn't exists")

class Unauthorized : Errors(401,"Unauthorized")

class GameNotBegun : Errors(400,"Game hasn't started ")

class GameAlreadyBegun : Errors( 400,"Game already started")