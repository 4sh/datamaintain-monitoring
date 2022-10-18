import type {Project} from "../domain/Project";
import type {EnvHierarchy, ProjectHierarchy} from "../domain/ProjectHierarchy";
import {EnvMock} from "./EnvMock";
import {ModuleMock} from "./ModuleMock";


export class ProjectMock {
    public static readonly projects: Project[] = [
        { id: 'dm', name: "Datamaintain"},
        { id: 'gh', name: "Github"},
        { id: 'fb', name: "Facebook"},
        { id: 'gg', name: "Google"},
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
