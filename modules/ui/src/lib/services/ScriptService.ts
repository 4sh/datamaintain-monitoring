import {Http} from '$lib/services/utils/HttpService';
 import {Script} from '$lib/domain/script/Script';

export class ScriptService {
    private static baseUrl = '/api/v1/scripts';
    public static byId = async (id: string): Promise<Script> => {
        return Http.get<Script>(`${ScriptService.baseUrl}/${id}`, Script)
    }
}
