import type {Module} from "../domain/Module";
import {ModuleMock} from "../mocks/ModuleMock";


export class ModuleService {
    public static byId(id: string): Module | undefined {
        return ModuleMock.modules.find(module => module.id === id);
    }
}
