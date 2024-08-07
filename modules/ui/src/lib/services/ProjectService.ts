import {ProjectHierarchy} from "$lib/domain/ProjectHierarchy";
import {Project} from "$lib/domain/Project";
import {Http} from "$lib/services/utils/HttpService";

export class ProjectService {
    private static baseUrl = '/api/v1/projects';
    public static byId = async (id: string): Promise<Project> => {
        return Http.get<Project>(`${ProjectService.baseUrl}/${id}`, Project)
    }

    public static projectHierarchies = async (): Promise<ProjectHierarchy[]> => {
        return Http.getAsArray<ProjectHierarchy>(`${ProjectService.baseUrl}/hierarchies`, ProjectHierarchy)
    }

    public static create = async (project: Project) => {
        return Http.post(`${ProjectService.baseUrl}`, {
            name: project.name,
            smallName: project.smallName
        });
    }

    public static updateName = async (project: Project): Promise<Project> => {
        return Http.put(`${ProjectService.baseUrl}/${project.id}/name`, {
            name: project.name,
            smallName: project.smallName
        });
    }
}
