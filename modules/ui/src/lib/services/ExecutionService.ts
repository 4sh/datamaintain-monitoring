import type {Execution, ExecutionForDashboard, ExecutionWithReport} from "$lib/domain/execution/Execution";
import {ExecutionMock} from "$lib/mocks/ExecutionMock";

export type ExecutionSearchRequest= {};

export class ExecutionService {
    public static async search(request: ExecutionSearchRequest): Promise<Execution[]>  {
        return new Promise((resolve) => {
            resolve(ExecutionMock.executions);
        });
    }

    public static async searchMostRecent(): Promise<ExecutionForDashboard[]>  {
        return new Promise((resolve) => {
            resolve(ExecutionMock.executionsForDashboard);
        });
    }

    public static async byId(id: string): Promise<ExecutionWithReport | undefined> {
        return new Promise((resolve) => {
            resolve(ExecutionMock.executionsWithReport
                .find(execution => execution.id === id));
        });
    }
}
