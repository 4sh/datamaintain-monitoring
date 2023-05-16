package dao.script

import generated.domain.tables.pojos.DmScript
import script.ScriptCreationRequest

fun buildDmScript(
    name: String = "myName",
    checksum: String = "checksum",
    content: String = "content"
) = DmScript(name = name, checksum = checksum, content = content)

fun buildScriptCreationRequest(
    name: String = "myName",
    checksum: String = "checksum",
    content: String = "content"
) = ScriptCreationRequest(name = name, checksum = checksum, content = content)