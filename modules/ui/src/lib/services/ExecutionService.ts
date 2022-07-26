import type {Execution} from "../domain/execution/Execution";
import {Origin} from "../domain/execution/Origin";
import {ExecutionType} from "../domain/execution/ExecutionType";
import {ExecutionStatus} from "../domain/execution/ExecutionStatus";

export type ExecutionSearchRequest= {};

export class ExecutionService {
    public static readonly executions: Execution[] = [
        {
            id: '1',
            date: new Date(),
            origin: Origin.SERVER,
            type: ExecutionType.PLANNED,
            status: ExecutionStatus.COMPLETED,
            report:
                {
                    scannedScripts: ["1-script1", "1-script2"],
                    filteredScripts: [],
                    prunedScripts: [],
                    executedScripts: [],
                    validatedCheckRules: []
                }
        },
        {
            id: '2',
            date: new Date(),
            origin: Origin.SERVER,
            type: ExecutionType.PLANNED,
            status: ExecutionStatus.COMPLETED,
            report:
                {
                    scannedScripts: ["2-script1", "2-script2"],
                    filteredScripts: [],
                    prunedScripts: [],
                    executedScripts: [],
                    validatedCheckRules: []
                }
        },
        {
            id: '3',
            date: new Date(),
            origin: Origin.SERVER,
            type: ExecutionType.PLANNED,
            status: ExecutionStatus.COMPLETED,
            report:
                {
                    scannedScripts: ["3-script1", "3-script2", "3-script3"],
                    filteredScripts: [],
                    prunedScripts: [],
                    executedScripts: [],
                    validatedCheckRules: []
                }
        }
    ];

    public static async search(request: ExecutionSearchRequest): Promise<Execution[]>  {
        return new Promise((resolve) => {
            resolve(ExecutionService.executions);
        });
    }

    public static async byId(id: String): Promise<Execution | undefined> {
        return new Promise((resolve) => {
            resolve(ExecutionService.executions
                .find(execution => execution.id === id));
        });
    }
}
