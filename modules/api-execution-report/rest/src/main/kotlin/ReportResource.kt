import org.http4k.contract.ContractRoute
import org.http4k.contract.bindContract
import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.format.Jackson.auto

class ReportResource: PublicResource {
    override fun routes(): List<ContractRoute> = listOf(
        ("reports") bindContract Method.POST to createReport()
    )

    private fun createReport() = { request: Request ->
        Response(OK).also { println(monitoringReportLens(request)) }
    }

    companion object {
        private val monitoringReportLens = Body.auto<MonitoringReport>().toLens()
    }
}