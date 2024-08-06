import type {Execution, ExecutionForDashboard, ExecutionWithReport} from "$lib/domain/execution/Execution";
import {ExecutionMock} from "$lib/mocks/ExecutionMock";
import {ScriptEnvMatrix} from '$lib/domain/script/ScriptEnvMatrix';
import {plainToInstance} from 'class-transformer'

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

    public static async scriptEnvMatrixByProjectAndModule(projectId: string, moduleId: string): Promise<ScriptEnvMatrix> {
        const res = await fetch(`/api/v1/scriptExecutions/projects/${projectId}/modules/${moduleId}`);
        return plainToInstance(ScriptEnvMatrix, (await res.json()) as string)
    }
}
