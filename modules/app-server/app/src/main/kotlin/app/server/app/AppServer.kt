package app.server.app

fun main(args: Array<String>) {
    helloWorld.start()

    io.ktor.server.netty.EngineMain.main(args)

    helloWorld.blockUntilShutdown()
}