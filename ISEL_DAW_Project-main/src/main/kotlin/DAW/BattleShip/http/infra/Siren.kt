package DAW.BattleShip.http.infra

import DAW.BattleShip.http.infra.SirenModel.Companion.MEDIA_TYPE
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity

import java.net.URI


class SirenBuilderScope<T>(
    val properties: T,
) {
    private val links = mutableListOf<LinkModel>()
    private val classes = mutableListOf<String>()
    private val actions = mutableListOf<ActionModel>()

    fun clazz(value: String) {
        classes.add(value)
    }

    fun link(href: String, rel: LinkRelation) {
        links.add(LinkModel(listOf(rel.value), URI(href).toASCIIString()))
    }

    fun action(name: String, href: URI, method: HttpMethod) {
        val scope = ActionBuilderScope(name, href, method)
        actions.add(scope.build())
    }

    fun build(): SirenModel<T> = SirenModel(
        clazz = classes,
        properties = properties,
        links = links,
        actions = actions
    )
}

class ActionBuilderScope(
    private val name: String,
    private val href: URI,
    private val method: HttpMethod,
) {
    fun build() = ActionModel(name, href.toASCIIString(), method.name)
}

fun <T> siren(value: T, block: SirenBuilderScope<T>.() -> Unit): SirenModel<T> {
    val scope = SirenBuilderScope(value)
    scope.block()
    return scope.build()
}

fun <T> SirenModel<T>.response(status: Int) = ResponseEntity
    .status(status)
    .header("Content-Type", MEDIA_TYPE)
    .body(this)

fun <T> SirenModel<T>.responseRedirect(status: Int, headerValues: String) = ResponseEntity
    .status(status)
    .header("Location", headerValues)
    .body(this)