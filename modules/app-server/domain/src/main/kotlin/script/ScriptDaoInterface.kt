package script

interface ScriptDaoInterface {
    fun insert(data: ScriptCreationRequest): Script
    fun delete(checksum: String)
    fun findOneByChecksum(checksum: String): Script?
}