import type {Env} from "./Env";
import type {ExecutionMetadata} from "./ExecutionMetadata";


export class ModuleEnvMatrix {
    entries: ModuleEnvEntry[]
    envs: Env[]

    constructor(envs: Env[]) {
        this.entries = []
        this.envs = envs
    }

    addModuleEnvEntry(moduleEnvEntry: ModuleEnvEntry): ModuleEnvMatrix {
        this.entries.push(moduleEnvEntry)
        return this
    }
}

export class ModuleEnvEntry {
    moduleId: string
    moduleName: string
    envs: EnvExecutionEntry[]

    constructor(moduleId: string, moduleName: string) {
        this.moduleId = moduleId
        this.moduleName = moduleName
        this.envs = []
    }

    addEnvModuleEntry(envExecutedScriptEntry: EnvExecutionEntry): ModuleEnvEntry {
        this.envs.push(envExecutedScriptEntry)
        return this;
    }

    getEnvExecutionEntryMetadata(envId: string): EnvExecutionEntry | undefined {
        return this.envs.find(env => env.envId === envId)
    }
}

export class EnvExecutionEntry {
    envId: string
    execution : ExecutionMetadata

    constructor(envId: string, envName: string, execution: ExecutionMetadata) {
        this.envId = envId
        this.execution = execution
    }
}
