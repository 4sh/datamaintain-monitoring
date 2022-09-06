import type {Execution} from "../domain/execution/Execution";
import {ExecutionOrigin, ExecutionStatus, ExecutionType} from "../domain/execution/Execution";
import type {ExecutionWithReport} from "../domain/execution/Execution";
import {ScriptMock} from "./ScriptMock";


export class ExecutionMock {
    public static readonly executions: Execution[] = [
        {
            id: '1',
            date: new Date(),
            origin: ExecutionOrigin.SERVER,
            type: ExecutionType.PLANNED,
            status: ExecutionStatus.COMPLETED
        },
        {
            id: '2',
            date: new Date(),
            origin: ExecutionOrigin.SERVER,
            type: ExecutionType.PLANNED,
            status: ExecutionStatus.COMPLETED
        },
        {
            id: '3',
            date: new Date(),
            origin: ExecutionOrigin.SERVER,
            type: ExecutionType.PLANNED,
            status: ExecutionStatus.COMPLETED
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
}
