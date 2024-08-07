import {Http} from '$lib/services/utils/HttpService';
import {ModuleEnvironmentToken} from '$lib/domain/ModuleEnvironmentToken';

export class ModuleEnvironmentTokenService {
    private static baseUrl = '/api/v1/moduleEnvironmentTokens';

    public static byModuleAndEnvironment = async (moduleRef: string, environmentRef: string): Promise<ModuleEnvironmentToken> => {
        return Http.get<ModuleEnvironmentToken>(`${ModuleEnvironmentTokenService.baseUrl}/modules/${moduleRef}/environments/${environmentRef}`, ModuleEnvironmentToken);
    }

    public static regenerateToken = async (moduleRef: string, environmentRef: string): Promise<ModuleEnvironmentToken> => {
        return Http.post<ModuleEnvironmentToken>(`${ModuleEnvironmentTokenService.baseUrl}/regenerateToken`, {
            moduleRef: moduleRef,
            environmentRef: environmentRef
        })
    }
}