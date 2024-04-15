package DAW.BattleShip.http

import DAW.BattleShip.http.infra.LinkRelation

object Rel {

    val SELF = LinkRelation("self")

    fun getLink(uri: String) = LinkRelation("${Uris.PATH}$uri")
}