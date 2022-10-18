import type {Env} from "../domain/Env";
import {EnvMock} from "../mocks/EnvMock";


export class EnvService {
    public static byId(id: string): Env | undefined {
        return EnvMock.envs.find(env => env.id === id);
    }
}
