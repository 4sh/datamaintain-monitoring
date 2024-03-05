import proto.ScriptApi
import proto.ScriptServiceGrpcKt
import proto.scriptCreationResponse
import script.ScriptCreationRequest
import script.ScriptService
import java.math.BigInteger
import java.security.MessageDigest

class ScriptGrpcServiceImpl(private val scriptService: ScriptService) :
    ScriptServiceGrpcKt.ScriptServiceCoroutineImplBase() {
    override suspend fun createScript(request: ScriptApi.ScriptCreationRequest): ScriptApi.ScriptCreationResponse =
        scriptCreationResponse { checksum = scriptService.insert(request.toScriptCreationRequest()).checksum }
}

private fun ScriptApi.ScriptCreationRequest.toScriptCreationRequest(): ScriptCreationRequest {
    return ScriptCreationRequest(
        name = name,
        checksum = content.hash(),
        content = content
    )
}

private fun String.hash(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}
