package dao.script

import script.ScriptCreationRequest

fun buildScriptCreationRequest(
    name: String = "myName",
    checksum: String = "checksum",
    content: String = "content"
) = ScriptCreationRequest(name = name, checksum = checksum, content = content)