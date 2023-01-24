import org.http4k.contract.ContractRoute
import org.http4k.contract.bindContract
import org.http4k.contract.div
import org.http4k.core.*
import org.http4k.core.Status.Companion.OK
import org.http4k.format.Jackson.auto
import org.http4k.lens.Path

class ExecutionResource: PublicResource {
    private val baseUrl = "executions"

    override fun routes(): List<ContractRoute> = listOf(
        "$baseUrl/start" bindContract Method.POST to startExecution(),
        "$baseUrl/stop" / Path.of("executionId") bindContract Method.PUT to stopExecution()
    )

    private fun startExecution() = { _: Request ->
        Response(OK).with(executionStartResponseLens of ExecutionStartResponse((0..10).random()))
    }

    private fun stopExecution() = { executionId: String -> { request: Request ->
        Response(OK).also {
            println("Execution with id $executionId has ended, it created report ${monitoringReportLens(request)}")
        }
    } }

    companion object {
        private val monitoringReportLens = Body.auto<MonitoringReport>().toLens()
        private val executionStartResponseLens = Body.auto<ExecutionStartResponse>().toLens()
    }
}