import type {Env} from "$lib/domain/Env";


export class EnvService {
    private static baseUrl = '/api/v1/environments';

    public static byId = async (id: string): Promise<Env> => {
        const res = await fetch(`${EnvService.baseUrl}/${id}`);
        return (await res.json()) as Env;
    }
}
