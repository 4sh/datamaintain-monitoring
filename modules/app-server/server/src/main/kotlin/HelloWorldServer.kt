import io.grpc.Server
import io.grpc.ServerBuilder

class HelloWorldServer(private val port: Int = 8081) {
    private val server: Server = ServerBuilder
        .forPort(port)
        .addService(HelloWorldGrpcServiceImpl())
        .build()

    fun start() {
        server.start()
        println("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("*** shutting down gRPC server since JVM is shutting down")
                this@HelloWorldServer.stop()
                println("*** server shut down")
            }
        )
    }

    private fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }
}