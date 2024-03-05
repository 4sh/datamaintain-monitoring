package api.execution.report.app

import api.execution.report.client.BatchExecutionGrpcClient
import api.execution.report.client.ModuleEnvironmentTokenGrpcClient
import api.execution.report.client.ScriptExecutionGrpcClient
import api.execution.report.client.ScriptGrpcClient
import api.execution.report.domain.module.batch.execution.BatchExecutionService
import api.execution.report.domain.module.environment.token.ModuleEnvironmentTokenService
import api.execution.report.domain.module.script.execution.ScriptExecutionService
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder

const val grpcPort = 50051

val channel: ManagedChannel = ManagedChannelBuilder.forAddress("localhost", grpcPort).usePlaintext().build()

val moduleEnvironmentTokenGrpcClient = ModuleEnvironmentTokenGrpcClient(channel)
val moduleEnvironmentTokenService = ModuleEnvironmentTokenService(repository = moduleEnvironmentTokenGrpcClient)

val batchExecutionGrpcClient = BatchExecutionGrpcClient(channel)

val batchExecutionService = BatchExecutionService(
    moduleEnvironmentTokenService = moduleEnvironmentTokenService,
    batchExecutionRepository = batchExecutionGrpcClient
)

val scriptExecutionRepository = ScriptExecutionGrpcClient(channel)
val scriptRepository = ScriptGrpcClient(channel)
val scriptExecutionService = ScriptExecutionService(
    scriptExecutionRepository = scriptExecutionRepository,
    scriptRepository = scriptRepository
)