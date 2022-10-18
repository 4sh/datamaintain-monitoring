import type {ProjectHierarchy} from "../domain/ProjectHierarchy";
import {ProjectMock} from "../mocks/ProjectMock";
import type {Project} from "../domain/Project";

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
