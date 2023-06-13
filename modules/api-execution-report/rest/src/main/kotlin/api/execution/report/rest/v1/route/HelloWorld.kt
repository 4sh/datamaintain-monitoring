package api.execution.report.rest.v1.route

import api.execution.report.client.HelloWorldClient
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class HelloWorld2(val message: String)

fun Route.helloWorld(client: HelloWorldClient) {
    get("/helloWorld") {
        call.respond("Hello World !")
    }

    get("/helloWorld2") {
        call.respond(HelloWorld2("Hello World 2 !"))
    }

    get ("/grpc") {
        client.helloWorld("Hello World !").let {
            call.respond(it.response)
        }
    }
}