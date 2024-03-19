package script

class ScriptService(
    private val scriptDao: ScriptDaoInterface
) {
    fun insert(data: ScriptCreationRequest): ScriptCreationResponse {
        return try {
            ScriptCreationSucceededResponse(scriptDao.insert(data))
        } catch (e: Exception) {
            ScriptCreationFailedResponse(
                data.checksum,
                e.message ?: "",
                checkNotNull(findOneByChecksum(checksum = data.checksum))
            )
        }
    }

    fun delete(checksum: String) = scriptDao.delete(checksum)

    fun findOneByChecksum(checksum: String): Script? =
        scriptDao.findOneByChecksum(checksum)
}