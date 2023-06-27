import type {Project} from "$lib/domain/Project";
import type {EnvHierarchy, ProjectHierarchy} from "$lib/domain/ProjectHierarchy";
import {EnvMock} from "./EnvMock";
import {ModuleMock} from "./ModuleMock";


export class ProjectMock {
    public static readonly projects: Project[] = [
        { id: 'dm', name: "Datamaintain", smallName: "Dm"},
        { id: 'gh', name: "Github", smallName: "GH"},
        { id: 'fb', name: "Facebook", smallName: "Fb"},
        { id: 'gg', name: "Google", smallName: "Gg"},
    ];

    public static getProjectHierarchies(): ProjectHierarchy[] {
        return this.projects
            .map(project => {
                return {
                    ...project,
                    envs: this.getEnvHierarchies(project.id)
                } as ProjectHierarchy
            });
    }

    private static getEnvHierarchies(projectId: string): EnvHierarchy[] {
        return EnvMock.byProjectId(projectId)
            .map(env => {
                return {
                    ...env,
                    modules: ModuleMock.byProjectIdAndEnv(projectId, env.id)
                } as EnvHierarchy
            });
    }
}
