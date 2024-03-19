package api.execution.report.domain.module.script.execution

interface ScriptRepository {
    suspend fun createScript(scriptContent: String, scriptName: String): String
}