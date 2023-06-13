package api.execution.report.client

import io.grpc.ManagedChannel
import proto.HelloWorldApi
import proto.HelloWorldServiceGrpcKt
import proto.helloWorldRequest
import java.io.Closeable
import java.util.concurrent.TimeUnit

class HelloWorldClient(private val channel: ManagedChannel) : Closeable {
    private val stub = HelloWorldServiceGrpcKt.HelloWorldServiceCoroutineStub(channel)

    suspend fun helloWorld(string: String): HelloWorldApi.HelloWorldResponse {
        val request = helloWorldRequest { message = string }
        return stub.helloWorld(request)
    }

    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}