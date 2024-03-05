package api.execution.report.client

import io.grpc.ManagedChannel
import java.io.Closeable
import java.util.concurrent.TimeUnit

abstract class AbstractGrpcClient(private val channel: ManagedChannel): Closeable {
    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}