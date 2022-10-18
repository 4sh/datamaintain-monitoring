import type {Module} from "../domain/Module";
import {ModuleMock} from "../mocks/ModuleMock";


export class ModuleService {
    public static byId(id: string): Promise<Module> {
        return new Promise<Module>((resolve, reject) => {
            const module = ModuleMock.modules.find(module => module.id === id);

            if (module) {
                resolve(module)
            } else {
                reject()
            }
        })
    }
}
