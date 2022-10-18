import type {Env} from "./Env";
import type {Module} from "./Module";
import type {Project} from "./Project";

export interface ProjectHierarchy extends Project {
    envs: EnvHierarchy[]
}

export interface EnvHierarchy extends Env {
    modules: Module[]
}

type SearchItemType =
    undefined // means never matches
    | null    // means always matches
    | string; // impose to proceed to match to know

export class SearchItem {
    searchItem: SearchItemType;
    mandatory: boolean;

    constructor(searchItem: SearchItemType, mandatory = true) {
        this.searchItem = this.refine(searchItem);
        this.mandatory = mandatory;
    }

    match(text: string): boolean {
        if (this.isClosed()) {
            return false;
        }

        if (this.isOpened()) {
            return true;
        }

        if (!text) {
            return false;
        }

        return this.normalize(text).startsWith(this.searchItem as string);
    }

    isClosed() : boolean {
        return this.searchItem === undefined;
    }

    isOpened() : boolean {
        return this.searchItem === null;
    }

    private refine(searchText: SearchItemType): SearchItemType {
        if (searchText === undefined) {
            return undefined;
        }

        if (searchText === null
            || searchText === ""
            || searchText === " "
            || searchText === "*") {
            return null;
        }

        return this.normalize(searchText);
    }

    private normalize(text: string): string {
        return text
            .trim()
            .toLocaleLowerCase()
            .normalize("NFD")
            .replace(/\p{Diacritic}/gu, "")
    }
}

export class HierarchySearch {
    project: SearchItem;
    env: SearchItem;
    module: SearchItem;

    constructor(searchText: string) {
        const split = searchText
            .replace(" / ", "/")
            .replace("/ ", "/")
            .replace(" /", "/")
            .split("/");

        if (split.length === 0) {
            this.project = new SearchItem(null);
            this.env = new SearchItem(null);
            this.module = new SearchItem(null);
        } else if (split.length === 1) {
            this.project = new SearchItem(split[0], false);
            this.env = new SearchItem(split[0], false);
            this.module = new SearchItem(split[0], false);
        } else if (split.length === 2) {
            this.project = new SearchItem(split[0]);
            this.env = new SearchItem(split[1], false);
            this.module = new SearchItem(split[1], false);
        } else {
            this.project = new SearchItem(split[0]);
            this.env = new SearchItem(split[1]);
            this.module = new SearchItem(split[2]);
        }
    }

    isEmpty() : boolean {
        return this.project.isOpened()
            && this.env.isOpened()
            && this.module.isOpened()
    }

    filter(projectHierarchies: ProjectHierarchy[]): ProjectHierarchy[] {
        if (!this.isEmpty()) {
            projectHierarchies = projectHierarchies
                .map(project => {
                    if (!this.project.isOpened()) {
                        if (this.project.mandatory) {
                            if (!this.project.match(project.name)) {
                                project.envs = [];
                                return project;
                            }
                        } else {
                            if (this.project.match(project.name)) {
                                return project;
                            }
                        }
                    }

                    project.envs = project.envs
                        .map(env => {
                            if (!this.env.isOpened()) {
                                if (this.env.mandatory) {
                                    if (!this.env.match(env.name)) {
                                        env.modules = [];
                                        return env;
                                    }
                                } else {
                                    if (this.env.match(env.name)) {
                                        return env;
                                    }
                                }
                            }

                            if (this.module !== null) {
                                env.modules = env.modules.filter(module => {
                                    return this.module.match(module.name)
                                });
                            }

                            return env;
                        })
                        .filter(env => {
                            return env.modules.length !== 0
                                || (!this.env.isOpened() && !this.module.mandatory && this.env.match(env.name))
                        });

                    return project;
                })
                .filter(project => {
                    return project.envs.length !== 0
                        || (!this.project.isOpened() && this.project.match(project.name))
                });
        }

        return projectHierarchies;
    }
}
