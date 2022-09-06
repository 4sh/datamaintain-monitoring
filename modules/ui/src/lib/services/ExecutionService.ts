import type {Execution, ExecutionWithReport} from "../domain/execution/Execution";
import {ExecutionMock} from "../mocks/ExecutionMock";

export type ExecutionSearchRequest= {};

export class ExecutionService {
    public static async search(request: ExecutionSearchRequest): Promise<Execution[]>  {
        return new Promise((resolve) => {
            resolve(ExecutionMock.executions);
        });
    }

    public static async byId(id: string): Promise<ExecutionWithReport | undefined> {
        return new Promise((resolve) => {
            resolve(ExecutionMock.executionsWithReport
                .find(execution => execution.id === id));
        });
    }
}
