import type {Module} from "$lib/domain/Module";


export class ModuleService {
    private static baseUrl = '/api/v1/modules';

    public static byId = async (id: string): Promise<Module> => {
        const res = await fetch(`${ModuleService.baseUrl}/${id}`);
        return (await res.json()) as Module;
    }
}
