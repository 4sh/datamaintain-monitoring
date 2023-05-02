import mu.KLogging
import org.http4k.contract.contract
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

object App : KLogging() {
    operator fun invoke(
        v1Resources: Set<V1Resource>
    ): RoutingHttpHandler =
        routes("/api/v1" bind contract {
            routes += v1Resources.flatMap { it.routes() }
        })
}