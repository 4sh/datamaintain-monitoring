import type {Env} from "../Env";
import type {ScriptExecutionMetadata} from "../ScriptExecutionMetadata";


export class ScriptEnvMatrix {
    entries: ScriptEnvEntry[]
    envs: Env[]

    constructor(envs: Env[]) {
        this.entries = []
        this.envs = envs
    }

    addScriptEnvEntry(scriptEnvEntry: ScriptEnvEntry): ScriptEnvMatrix {
        this.entries.push(scriptEnvEntry)
        return this
    }
}

export class ScriptEnvEntry {
    scriptId: string
    scriptName: string
    envs: EnvExecutedScriptEntry[]

    constructor(scriptId: string, scriptName: string) {
        this.scriptId = scriptId
        this.scriptName = scriptName
        this.envs = []
    }

    addEnvExecutedScriptEntry(envExecutedScriptEntry: EnvExecutedScriptEntry): ScriptEnvEntry {
        this.envs.push(envExecutedScriptEntry)
        return this;
    }

    getScriptExecutionMetadata(envId: string): EnvExecutedScriptEntry | undefined {
        return this.envs.find(env => env.envId === envId)
    }
}

export class EnvExecutedScriptEntry {
    envId: string
    execution : ScriptExecutionMetadata

    constructor(envId: string, envName: string, execution: ScriptExecutionMetadata) {
        this.envId = envId
        this.execution = execution
    }
}
