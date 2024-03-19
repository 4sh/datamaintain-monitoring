package script

sealed interface ScriptCreationResponse

data class ScriptCreationFailedResponse(
    val checksum: String,
    val failedReason: String,
    val alreadyInsertedScript: Script
): ScriptCreationResponse

data class ScriptCreationSucceededResponse(val createdScript: Script): ScriptCreationResponse