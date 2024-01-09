package script.execution

import java.util.*

class ScriptExecutionService(
    private val scriptExecutionDao: ScriptExecutionDaoInterface
) {
    fun insert(data: ScriptExecutionCreationRequest): ScriptExecution =
        scriptExecutionDao.insert(data)

    fun updateScriptExecutionStartData(
        scriptExecutionId: UUID,
        executionStartData: ScriptExecutionStartUpdateRequest
    ): ScriptExecution? =
        scriptExecutionDao.updateScriptExecutionStartData(scriptExecutionId, executionStartData)

    fun updateScriptExecutionEndData(
        scriptExecutionId: UUID,
        executionEndData: ScriptExecutionEndUpdateRequest
    ): ScriptExecution? =
        scriptExecutionDao.updateScriptExecutionEndData(scriptExecutionId, executionEndData)

    fun delete(id: UUID) = scriptExecutionDao.delete(id)

    fun findOneById(id: UUID): ScriptExecution? = scriptExecutionDao.findOneById(id)

    fun findOneDetailById(id: UUID): ScriptExecutionDetail? = scriptExecutionDao.findOneDetailById(id)
}