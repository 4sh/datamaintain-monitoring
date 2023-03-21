import type {ProjectHierarchy} from "$lib/domain/ProjectHierarchy";
import {ProjectMock} from "$lib/mocks/ProjectMock";
import type {Project} from "$lib/domain/Project";

export class ProjectService {
    public static byId(id: string): Promise<Project> {
        return new Promise<Project>((resolve, reject) => {
            const project = ProjectMock.projects.find(project => project.id === id);

            if (project) {
                resolve(project)
            } else {
                reject()
            }
        })
    }

    public static projectHierarchies(): Promise<ProjectHierarchy[]> {
        return new Promise<ProjectHierarchy[]>((resolve) => {
            resolve(ProjectMock.getProjectHierarchies())
        })
    }
}
