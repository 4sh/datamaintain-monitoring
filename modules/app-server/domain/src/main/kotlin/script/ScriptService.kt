package script

class ScriptService(
    private val scriptDao: ScriptDaoInterface
) {
    fun insert(data: ScriptCreationRequest): Script =
        scriptDao.insert(data)

    fun delete(checksum: String) = scriptDao.delete(checksum)

    fun findOneByChecksum(checksum: String): Script? =
        scriptDao.findOneByChecksum(checksum)
}