import type {Execution, ExecutionForDashboard} from "$lib/domain/execution/Execution";
import {ExecutionDetail} from "$lib/domain/execution/Execution";
import {ExecutionMock} from "$lib/mocks/ExecutionMock";
import {ScriptEnvMatrix} from '$lib/domain/script/ScriptEnvMatrix';
import {Http} from '$lib/services/utils/HttpService';

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
        return Http.get<ScriptEnvMatrix>(`/api/v1/scriptExecutions/projects/${projectId}/modules/${moduleId}`, ScriptEnvMatrix)
    }
}
