import org.http4k.contract.ContractRoute

interface BaseResource {
    fun routes(): List<ContractRoute>
}

interface V1Resource: BaseResource