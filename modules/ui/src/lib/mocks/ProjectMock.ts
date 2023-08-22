import type {Project} from "$lib/domain/Project";
import type {EnvHierarchy, ProjectHierarchy} from "$lib/domain/ProjectHierarchy";
import {EnvMock} from "./EnvMock";
import {ModuleMock} from "./ModuleMock";

export class ProjectMock {
    public static readonly datamaintainProject = { id: 'dm', name: "Datamaintain", smallName: "Dm"};
    public static readonly githubProject = { id: 'gh', name: "Github", smallName: "GH"};
    public static readonly facebookProject = { id: 'fb', name: "Facebook", smallName: "Fb"};
    public static readonly googleProject = { id: 'gg', name: "Google", smallName: "Gg"};

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
