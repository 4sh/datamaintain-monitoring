package api.execution.report.client

import api.execution.report.domain.module.script.execution.ScriptRepository
import io.grpc.ManagedChannel
import proto.ScriptServiceGrpcKt
import proto.scriptCreationRequest

class ScriptGrpcClient(channel: ManagedChannel) : AbstractGrpcClient(channel), ScriptRepository {
    private val stub = ScriptServiceGrpcKt.ScriptServiceCoroutineStub(channel)
    override suspend fun createScript(scriptContent: String, scriptName: String): String =
        stub.createScript(scriptCreationRequest {
            content = scriptContent
            name = scriptName
        }).checksum
}