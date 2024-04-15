package DAW.BattleShip.http.utils

import java.security.MessageDigest

class Hashing{
    fun cypher(password : String) : String = password.hashCode().toString()
}
