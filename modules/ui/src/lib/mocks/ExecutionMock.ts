import type {Execution, ExecutionForDashboard} from "$lib/domain/execution/Execution";
import {ExecutionOrigin, ExecutionStatus, ExecutionType} from "$lib/domain/execution/Execution";
import type {ExecutionWithReport} from "$lib/domain/execution/Execution";
import {ScriptMock} from "./ScriptMock";
import {
    EnvExecutedScriptEntry,
    ScriptEnvEntry,
    ScriptEnvMatrix
} from "$lib/domain/script/ScriptEnvMatrix";
import {EnvMock} from "./EnvMock";
import {EnvExecutionEntry, ModuleEnvEntry, ModuleEnvMatrix} from "$lib/domain/ModuleEnvMatrix";
import {ModuleMock} from "./ModuleMock";
import {ScriptExecutionMetadata} from "$lib/domain/ScriptExecutionMetadata";
import {ExecutionMetadata} from "$lib/domain/ExecutionMetadata";
import {ProjectMock} from "$lib/mocks/ProjectMock";


export class ExecutionMock {
    public static readonly executions: Execution[] = [
        {
            id: '1',
            date: new Date(),
            origin: ExecutionOrigin.SERVER,
            type: ExecutionType.PLANNED,
            status: ExecutionStatus.COMPLETED,
            duration: 125
        },
        {
            id: '2',
            date: new Date(),
            origin: ExecutionOrigin.SERVER,
            type: ExecutionType.PLANNED,
            status: ExecutionStatus.ERROR,
            duration: 361
        },
        {
            id: '3',
            date: new Date(),
            origin: ExecutionOrigin.SERVER,
            type: ExecutionType.PLANNED,
            status: ExecutionStatus.IN_PROGRESS,
            duration: 88
        }
    ];

    public static readonly executionsWithReport: ExecutionWithReport[] = [
        {
            ...ExecutionMock.executions[0],
            report:
                {
                    scannedScripts: [ScriptMock.scripts[0], ScriptMock.scripts[1]],
                    filteredScripts: [ScriptMock.scripts[0]],
                    prunedScripts: [ScriptMock.scripts[0]],
                    executedScripts: [
                        ScriptMock.toExecutedScript(ScriptMock.scripts[0], ExecutionStatus.COMPLETED, 123, [])
                    ],
                    validatedCheckRules: []
                }
        },
        {
            ...ExecutionMock.executions[1],
            report:
                {
                    scannedScripts: [ScriptMock.scripts[0], ScriptMock.scripts[1]],
                    filteredScripts: [ScriptMock.scripts[0], ScriptMock.scripts[1]],
                    prunedScripts: [ScriptMock.scripts[1]],
                    executedScripts: [
                        ScriptMock.toExecutedScript(ScriptMock.scripts[1], ExecutionStatus.COMPLETED, 346, [])
                    ],
                    validatedCheckRules: []
                }
        },
        {
            ...ExecutionMock.executions[2],
            report:
                {
                    scannedScripts: [ScriptMock.scripts[0], ScriptMock.scripts[1], ScriptMock.scripts[2], ScriptMock.scripts[3]],
                    filteredScripts: [ScriptMock.scripts[0], ScriptMock.scripts[1], ScriptMock.scripts[2], ScriptMock.scripts[3]],
                    prunedScripts: [ScriptMock.scripts[2], ScriptMock.scripts[3]],
                    executedScripts: [
                        ScriptMock.toExecutedScript(ScriptMock.scripts[2], ExecutionStatus.COMPLETED, 8976, []),
                        ScriptMock.toExecutedScript(ScriptMock.scripts[3], ExecutionStatus.ERROR, 98765, [])
                    ],
                    validatedCheckRules: []
                }
        }
    ];

    public static readonly executionsForDashboard: ExecutionForDashboard[] = [
        {
            ...ExecutionMock.executions[0],
            project: {id: ProjectMock.projects[0].id, name: ProjectMock.projects[0].name, smallName: ProjectMock.projects[0].smallName},
            module: {id: ModuleMock.modules[0].id, name: ModuleMock.modules[0].name},
            env: {id: EnvMock.envs[0].id, name: EnvMock.envs[0].name, smallName: EnvMock.envs[0].smallName},
            nbScriptsKO: 5,
            nbScriptsOK: 11
        },
        {
            ...ExecutionMock.executions[1],
            project: {id: ProjectMock.projects[1].id, name: ProjectMock.projects[1].name, smallName: ProjectMock.projects[1].smallName},
            module: {id: ModuleMock.modules[1].id, name: ModuleMock.modules[1].name},
            env: {id: EnvMock.envs[3].id, name: EnvMock.envs[3].name, smallName: EnvMock.envs[3].smallName},
            nbScriptsKO: 0,
            nbScriptsOK: 12
        },
        {
            ...ExecutionMock.executions[2],
            project: {id: ProjectMock.projects[0].id, name: ProjectMock.projects[0].name, smallName: ProjectMock.projects[2].smallName},
            module: {id: ModuleMock.modules[0].id, name: ModuleMock.modules[0].name},
            env: {id: EnvMock.envs[0].id, name: EnvMock.envs[0].name, smallName: EnvMock.envs[0].smallName},
            nbScriptsKO: 1,
            nbScriptsOK: 23
        }
    ];

    public static scriptEnvMatrixByProject(projectId: string): ScriptEnvMatrix {
        const matrix = new ScriptEnvMatrix(EnvMock.byProjectId(projectId));

        ScriptMock.scripts.forEach(script => {
            const scriptEnvEntry = new ScriptEnvEntry(script.identifier, script.name);

            EnvMock.byProjectId(projectId).forEach(env => {
                const number = Math.floor(Math.random() * 4);

                if (number > 0) {
                    const execution = this.executions[number - 1]
                    const scriptExecutionMetadata = new ScriptExecutionMetadata(execution.id, execution.date, execution.duration, script.identifier)

                    scriptEnvEntry.addEnvExecutedScriptEntry(
                        new EnvExecutedScriptEntry(env.id, env.name, scriptExecutionMetadata))
                }
            })

            matrix.addScriptEnvEntry(scriptEnvEntry);
        })

        return matrix;
    }

    public static moduleEnvMatrixByProject(projectId: string): ModuleEnvMatrix {
        const matrix = new ModuleEnvMatrix(EnvMock.byProjectId(projectId));

        ModuleMock.byProjectId(projectId).forEach(module => {
            const moduleEnvEntry = new ModuleEnvEntry(module.id, module.name);

            EnvMock.byProjectId(projectId).forEach(env => {
                const number = Math.floor(Math.random() * ExecutionMock.executions.length);

                if (number > 0) {
                    const execution = this.executions[number - 1]
                    const executionMetadata = new ExecutionMetadata(
                        execution.id, execution.date, execution.duration, execution.status)

                    moduleEnvEntry.addEnvModuleEntry(
                        new EnvExecutionEntry(env.id, env.name, executionMetadata))
                }
            })

            matrix.addModuleEnvEntry(moduleEnvEntry);
        })

        return matrix;
    }
}
