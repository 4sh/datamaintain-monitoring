import type {Env} from "../Env";
import {ScriptExecutionMetadata} from "../ScriptExecutionMetadata";
import {Type} from 'class-transformer';
import 'reflect-metadata';

export class ScriptEnvMatrix {
    @Type(() => ScriptEnvEntry)
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
    @Type(() => EnvExecutedScriptEntry)
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
    @Type(() => ScriptExecutionMetadata)
    execution : ScriptExecutionMetadata

    constructor(envId: string, envName: string, execution: ScriptExecutionMetadata) {
        this.envId = envId
        this.execution = execution
    }
}
