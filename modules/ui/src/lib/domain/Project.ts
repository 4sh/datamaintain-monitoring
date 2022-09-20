import type {Env} from "./Env";
import type {Module} from "./Module";

export type Project = {
    id: string,
    name: string
};

export interface ProjectHierarchy extends Project {
    envs: EnvHierarchy[]
}

export interface EnvHierarchy extends Env {
    modules: Module[]
}