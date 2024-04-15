package DAW.BattleShip.domain


data class Player(val id : Int,val username : String, val password : String, val points : Double = 1500.0, val token : String)


