package execution.script

import java.util.*

interface ScriptExecutionDaoInterface {
    fun insert(data: ScriptExecutionCreationRequest): ScriptExecution
    fun updateScriptExecutionStartData(
        scriptExecutionId: UUID,
        executionStartData: ScriptExecutionStartUpdateRequest
    ): ScriptExecution?
    fun updateScriptExecutionEndData(
        scriptExecutionId: UUID,
        executionEndData: ScriptExecutionEndUpdateRequest
    ): ScriptExecution?
    fun delete(id: UUID)
    fun findOneById(id: UUID): ScriptExecution?
    fun findOneDetailById(id: UUID): ScriptExecutionDetail?
    fun find(searchRequest: ScriptExecutionSearchRequest): List<ScriptExecutionListItem>
    fun findModuleScriptsExecutionsInformation(moduleRef: UUID): List<ScriptWithAllExecutions>
    fun findDetailByBatchExecutionId(batchExecutionId: UUID): List<ScriptExecutionDetail>
}