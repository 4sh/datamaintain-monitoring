import type {ProjectHierarchy} from "$lib/domain/ProjectHierarchy";
import type {Project} from "$lib/domain/Project";

export class ProjectService {
    private static baseUrl = '/api/v1/projects';
    public static byId = async (id: string): Promise<Project> => {
        const res = await fetch(`${ProjectService.baseUrl}/${id}`);
        return (await res.json()) as Project;
    }

    public static projectHierarchies = async (): Promise<ProjectHierarchy[]> => {
        const res = await fetch(`${ProjectService.baseUrl}/hierarchies`);
        return (await res.json()) as ProjectHierarchy[];
    }
}
