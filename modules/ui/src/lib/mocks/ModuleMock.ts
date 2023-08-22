import type {Module} from "$lib/domain/Module";
import {ProjectMock} from './ProjectMock';


export class ModuleMock {
    public static readonly dmModules = [
        { id: 'a78e96a7-6748-4f01-9691-ea3bf851ad43', name: "Datamaintain"}
    ];

    public static readonly ghModules = [
        { id: '70dc790e-40d8-4d80-9500-75173629e47f', name: "GithubPages"},
        { id: 'abe7e583-d3e8-49f5-9462-e5e8f85a36aa', name: "PRs"}
    ];

    public static readonly fbModules = [
        { id: '2c5951f2-de99-4832-ba3e-8c6406675254', name: "Facebook"},
        { id: '05332ada-940d-4913-b51c-b4b4e4012ff9', name: "Instagram"}
    ];

    public static readonly ggModules = [
        { id: '52db1224-e09c-4d1b-a049-bab122faacd4', name: "YouTube"},
        { id: 'f50781d4-2f29-4338-9f39-a79d39f90614', name: "Google Play"},
        { id: '80ba4570-86c6-4e92-aaaf-9010207e1d56', name: "Google Store"},
        { id: '0cb21985-4e42-4e35-9a2e-b8061dfdb916', name: "ApiGee"}
    ];

    public static readonly modules: Module[] = [
        ...this.dmModules,
        ...this.ghModules,
        ...this.fbModules,
        ...this.ggModules
    ];

    public static byProjectId(projectId: string): Module[] {
        if (projectId === ProjectMock.datamaintainProject.id) {
            return ModuleMock.dmModules;
        }

        if (projectId === ProjectMock.githubProject.id) {
            return ModuleMock.ghModules;
        }
        if (projectId === ProjectMock.facebookProject.id) {
            return ModuleMock.fbModules;
        }

        if (projectId === ProjectMock.googleProject.id) {
            return ModuleMock.ggModules;
        }

        return [];
    }

    public static byProjectIdAndEnv(projectId: string, envId: string): Module[] {
        switch (projectId) {
            case ProjectMock.datamaintainProject.id:
                switch (envId) {
                    case '109a2c08-e836-451a-86ef-d67be8ffc648': return this.dmModules;
                    case 'bc25a6de-edf7-4325-981a-36a2e20beee1': return this.dmModules;
                    case 'c1a064ae-84e3-468a-85c4-4a9b404158e4': return this.dmModules;
                    default: return [];
                }
            case ProjectMock.githubProject.id:
                switch (envId) {
                    case '1a72f8ae-e4cf-4812-8876-8c3eb4a3ee65': return this.ghModules;
                    case 'b7a6259d-80eb-4c85-af47-0f19b7ab126d': return this.ghModules;
                    case '72bba570-ca2f-446b-a5ea-e3988e5b8918': return [
                        this.ghModules[0]
                    ];
                    default: return [];
                }
            case ProjectMock.facebookProject.id:
                switch (envId) {
                    case 'f-p': return this.fbModules;
                    default: return [];
                }
            case ProjectMock.googleProject.id:
                switch (envId) {
                    case 'd900abc9-032b-43f6-8666-0454d175ab10': return [
                        this.ggModules[0],
                        this.ggModules[1],
                        this.ggModules[2],
                    ];
                    case 'bfe80987-df78-4338-ae00-2d601e694b41': return [
                        this.ggModules[2],
                        this.ggModules[3]
                    ];
                    default: return [];
                }
        }

        // to avoid tslint error
        return [];
    }
}
