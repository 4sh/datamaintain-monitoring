package rest.v1.route

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class HelloWorld2(val message: String)

internal fun Route.helloWorld() {
    get("/helloWorld") {
        call.respond("Hello World !")
    }

    get("/helloWorld2") {
        call.respond(HelloWorld2("Hello World 2 !"))
    }
}