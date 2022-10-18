import type {Env} from "../domain/Env";
import {EnvMock} from "../mocks/EnvMock";


export class EnvService {
    public static byId(id: string): Promise<Env> {
        return new Promise<Env>((resolve, reject) => {
            const env = EnvMock.envs.find(env => env.id === id);

            if (env) {
                resolve(env)
            } else {
                reject()
            }
        })
    }
}
