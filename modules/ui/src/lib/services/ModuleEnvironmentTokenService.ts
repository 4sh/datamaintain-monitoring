import type {ModuleEnvironmentToken} from '$lib/domain/ModuleEnvironmentToken';
import {Http} from '$lib/services/utils/HttpService';

export class ModuleEnvironmentTokenService {
    private static baseUrl = '/api/v1/moduleEnvironmentTokens';

    public static byModuleAndEnvironment = async (moduleRef: string, environmentRef: string): Promise<ModuleEnvironmentToken> => {
        return Http.get<ModuleEnvironmentToken>(`${ModuleEnvironmentTokenService.baseUrl}/byModuleAndEnvironmentRef/${moduleRef}/${environmentRef}`);
    }

    public static regenerateToken = async (moduleRef: string, environmentRef: string): Promise<ModuleEnvironmentToken> => {
        return Http.post<ModuleEnvironmentToken>(`${ModuleEnvironmentTokenService.baseUrl}/regenerateToken`, {
            moduleRef: moduleRef,
            environmentRef: environmentRef
        })
    }
}