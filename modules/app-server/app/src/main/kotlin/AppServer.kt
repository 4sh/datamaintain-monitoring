import org.http4k.server.Netty
import org.http4k.server.asServer

fun main() {
    app.asServer(Netty(8080)).start()
}