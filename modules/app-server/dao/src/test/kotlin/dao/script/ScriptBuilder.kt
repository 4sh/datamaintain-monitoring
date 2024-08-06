package dao.script

import randomString
import script.ScriptCreationRequest

fun buildScriptCreationRequest(
    name: String = randomString(),
    checksum: String = randomString(),
    content: String = randomString()
) = ScriptCreationRequest(name = name, checksum = checksum, content = content)