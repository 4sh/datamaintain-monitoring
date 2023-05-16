package script

data class ScriptCreationRequest(
    val name: String,
    val checksum: String,
    val content: String
)