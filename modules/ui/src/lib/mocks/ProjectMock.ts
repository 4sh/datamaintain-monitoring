import type {Project} from "$lib/domain/Project";
import type {EnvHierarchy, ProjectHierarchy} from "$lib/domain/ProjectHierarchy";
import {EnvMock} from "./EnvMock";
import {ModuleMock} from "./ModuleMock";

export class ProjectMock {
    public static readonly datamaintainProject = { id: 'ecee7679-7197-432d-85a0-2d2d4da6096e', name: "Datamaintain", smallName: "Dm"};
    public static readonly githubProject = { id: '6b383cf5-0712-478e-b590-9f82965476b5', name: "Github", smallName: "GH"};
    public static readonly facebookProject = { id: '4b9cf32b-ff4f-436d-9796-438335653fa3', name: "Facebook", smallName: "Fb"};
    public static readonly googleProject = { id: '5e586e50-80e5-4a22-a91e-e266c3e01138', name: "Google", smallName: "Gg"};

    public static readonly projects: Project[] = [
        ProjectMock.datamaintainProject,
        ProjectMock.githubProject,
        ProjectMock.facebookProject,
        ProjectMock.googleProject,
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
