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

    public static create = async (project: Project) => {
        const res = await fetch(`${ProjectService.baseUrl}`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                name: project.name,
                smallName: project.smallName
            })
        });
        return (await res.json()) as Project;
    }

    public static updateName = async (project: Project): Promise<Project> => {
        const res = await fetch(`${ProjectService.baseUrl}/${project.id}/name`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                name: project.name,
                smallName: project.smallName
            })
        });
        return (await res.json()) as Project;
    }
}
