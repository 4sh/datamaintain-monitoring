package execution.script

import environment.EnvironmentService
import java.util.*

class ScriptExecutionService(
    private val scriptExecutionDao: ScriptExecutionDaoInterface,
    private val environmentService: EnvironmentService
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

    fun find(searchRequest: ScriptExecutionSearchRequest): List<ScriptExecutionListItem> = scriptExecutionDao.find(searchRequest)

    fun findModuleScriptsExecutionsInformation(projectRef: UUID, moduleRef: UUID): ModuleScriptsExecutionsInformation {
        return ModuleScriptsExecutionsInformation(
            scriptExecutionDao.findModuleScriptsExecutionsInformation(moduleRef),
            environmentService.findAllForProject(projectRef)
        )
    }
}