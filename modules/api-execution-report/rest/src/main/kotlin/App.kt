import mu.KLogging
import org.http4k.contract.contract
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

object App : KLogging() {
    operator fun invoke(
        publicResources: Set<PublicResource>
    ): RoutingHttpHandler =
        routes("/api/public" bind contract {
            routes += publicResources.flatMap { it.routes() }
        })
}