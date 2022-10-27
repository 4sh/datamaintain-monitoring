import type {Execution} from "../domain/execution/Execution";
import {ExecutionOrigin, ExecutionStatus, ExecutionType} from "../domain/execution/Execution";
import type {ExecutionWithReport} from "../domain/execution/Execution";
import {ScriptMock} from "./ScriptMock";
import {
    EnvExecutedScriptEntry,
    ScriptEnvEntry,
    ScriptEnvMatrix,
    ScriptExecutionMetadata
} from "../domain/script/ScriptEnvMatrix";
import {EnvMock} from "./EnvMock";


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
            status: ExecutionStatus.COMPLETED,
            duration: 361
        },
        {
            id: '3',
            date: new Date(),
            origin: ExecutionOrigin.SERVER,
            type: ExecutionType.PLANNED,
            status: ExecutionStatus.COMPLETED,
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
}
