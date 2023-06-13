package api.execution.report.rest.v1.route

import api.execution.report.client.HelloWorldClient
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class HelloWorld2(val message: String)

class HelloWorld(private val client: HelloWorldClient) {
    fun helloWorld(route: Route) {
        route.get("/helloWorld") {
            call.respond("Hello World !")
        }

        route.get("/helloWorld2") {
            call.respond(HelloWorld2("Hello World 2 !"))
        }

        route.get ("/grpc") {
            client.helloWorld("Hello World !").let {
                call.respond(it.response)
            }
        }
    }
}