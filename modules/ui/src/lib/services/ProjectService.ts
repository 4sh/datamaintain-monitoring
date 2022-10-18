import type {ProjectHierarchy} from "../domain/ProjectHierarchy";
import {ProjectMock} from "../mocks/ProjectMock";
import type {Project} from "../domain/Project";

export type ProjectSearchRequest = { q: string };

export class ProjectService {
    public static search(request: ProjectSearchRequest, skip?: number, limit?: number): Project[]  {
        let projects = ProjectMock.projects;

        if (request.q) {
            projects = projects
                .filter(project =>
                    project.name.startsWith(request.q)
                );
        }

        if(skip) {
            projects = projects.slice(skip, projects.length)
        }

        if(limit) {
            projects = projects.slice(0, limit)
        }

        return projects;
    }

    public static count(request: ProjectSearchRequest): number  {
        let projects = ProjectMock.projects;

        if (request.q) {
            projects = projects
                .filter(project =>
                    project.name.startsWith(request.q)
                );
        }

        return projects.length;
    }

    public static byId(id: string): Project | undefined {
        return ProjectMock.projects
            .find(project => project.id === id);
    }

    public static projectHierarchies(): ProjectHierarchy[] {
        return ProjectMock.getProjectHierarchies();
    }
}
