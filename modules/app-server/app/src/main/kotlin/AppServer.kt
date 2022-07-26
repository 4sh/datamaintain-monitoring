import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.server.Netty
import org.http4k.server.asServer

fun main() {
    val app = { request: Request ->
        Response(OK).also { println(request) }
    }
    app.asServer(Netty(8080)).start()
}