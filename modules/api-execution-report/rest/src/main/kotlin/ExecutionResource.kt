import datamaintain.monitoring.api.execution.report.api.ExecutionStartResponse
import datamaintain.monitoring.api.execution.report.api.MonitoringReport
import datamaintain.monitoring.api.execution.report.api.ScriptExecutionStart
import datamaintain.monitoring.api.execution.report.api.ScriptExecutionStop
import org.http4k.contract.ContractRoute
import org.http4k.contract.bindContract
import org.http4k.contract.div
import org.http4k.core.*
import org.http4k.core.Status.Companion.OK
import org.http4k.format.Jackson.auto
import org.http4k.lens.Path

class ExecutionResource: V1Resource {
    private val baseUrl = "executions"

    override fun routes(): List<ContractRoute> = listOf(
        "$baseUrl/start" bindContract Method.POST to startExecution(),
        "$baseUrl/stop" / Path.of("executionId") bindContract Method.PUT to stopExecution(),
        baseUrl / Path.of("executionId") / "scripts" / "start" bindContract Method.POST to startScriptExecution(),
        baseUrl / Path.of("executionId") / "scripts" / "stop" bindContract Method.POST to stopScriptExecution()
    )

    private fun startExecution() = { _: Request ->
        Response(OK).with(executionStartResponseLens of ExecutionStartResponse((0..10).random()))
    }

    private fun stopExecution() = { executionId: String -> { request: Request ->
        Response(OK).also {
            println("Execution with id $executionId has ended, it created report ${monitoringReportLens(request)}")
        }
    } }

    private fun startScriptExecution() = { executionId: String, _: String, _: String -> { request: Request ->
        val scriptExecutionStart = scriptExecutionStartLens(request)
        println("Start script execution $scriptExecutionStart for batch $executionId")
        Response(OK)
    } }

    private fun stopScriptExecution() = { executionId: String, _: String, _: String -> { request: Request ->
        val scriptExecutionStop = scriptExecutionStopLens(request)
        println("Stop script execution $scriptExecutionStop for batch $executionId")
        Response(OK)
    } }

    companion object {
        private val monitoringReportLens = Body.auto<MonitoringReport>().toLens()
        private val executionStartResponseLens = Body.auto<ExecutionStartResponse>().toLens()
        private val scriptExecutionStartLens = Body.auto<ScriptExecutionStart>().toLens()
        private val scriptExecutionStopLens = Body.auto<ScriptExecutionStop>().toLens()
    }
}