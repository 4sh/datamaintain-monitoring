package app.server.app

fun main(args: Array<String>) {
    grpcServer.start()

    io.ktor.server.netty.EngineMain.main(args)

    grpcServer.blockUntilShutdown()
}