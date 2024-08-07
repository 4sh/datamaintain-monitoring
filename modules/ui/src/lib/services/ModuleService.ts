import {Module} from "$lib/domain/Module";
import {Http} from "$lib/services/utils/HttpService";


export class ModuleService {
    private static baseUrl = '/api/v1/modules';

    public static byId = async (id: string): Promise<Module> => {
        return Http.get<Module>(`${ModuleService.baseUrl}/${id}`, Module);
    }

    public static create = async (projectRef: string, module: Module) => {
        return Http.post(`${ModuleService.baseUrl}`, {
            name: module.name,
            projectRef
        });
    }

    public static updateName = async (module: Module): Promise<Module> => {
        return Http.put(`${ModuleService.baseUrl}/${module.id}/name`, {
            name: module.name
        });
    }
}
