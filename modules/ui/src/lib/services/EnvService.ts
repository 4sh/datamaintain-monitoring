import type {Env} from "$lib/domain/Env";
import {Http} from "$lib/services/utils/HttpService";


export class EnvService {
    private static baseUrl = '/api/v1/environments';

    public static byId = async (id: string): Promise<Env> => {
        const res = await fetch(`${EnvService.baseUrl}/${id}`);
        return (await res.json()) as Env;
    }


    public static create = async (projectRef: string, env: Env) => {
        return Http.post(`${EnvService.baseUrl}`, {
            name: env.name,
            smallName: env.smallName,
            projectRef
        });
    }

    public static updateName = async (env: Env): Promise<Env> => {
        return Http.put(`${EnvService.baseUrl}/${env.id}/name`, {
            name: env.name,
            smallName: env.smallName
        });
    }
}
