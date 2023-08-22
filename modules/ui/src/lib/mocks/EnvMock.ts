import type {Env} from "$lib/domain/Env";
import {ProjectMock} from './ProjectMock';


export class EnvMock {
    public static readonly dmEnvs = [
        {id: '109a2c08-e836-451a-86ef-d67be8ffc648', name: "QA", smallName: "Qa"},
        {id: 'bc25a6de-edf7-4325-981a-36a2e20beee1', name: "Preprod", smallName: "Pp"},
        {id: 'c1a064ae-84e3-468a-85c4-4a9b404158e4', name: "Prod", smallName: "P"}
    ];

    public static readonly ghEnvs = [
        {id: '1a72f8ae-e4cf-4812-8876-8c3eb4a3ee65', name: "QA", smallName: "Qa"},
        {id: 'b7a6259d-80eb-4c85-af47-0f19b7ab126d', name: "Preprod", smallName: "Pp"},
        {id: '72bba570-ca2f-446b-a5ea-e3988e5b8918', name: "Prod", smallName: "P"}
    ];

    public static readonly fbEnvs = [
        { id: 'ef718993-ba5a-4493-9b9d-e213e154fba4', name: "Prod", smallName: "P"}
    ];

    public static readonly ggEnvs = [
        { id: 'd900abc9-032b-43f6-8666-0454d175ab10', name: "Qualification", smallName: "Qa"},
        { id: 'bfe80987-df78-4338-ae00-2d601e694b41', name: "Prod", smallName: "P"}
    ];

    public static readonly envs: Env[] = [
        ...this.dmEnvs,
        ...this.ghEnvs,
        ...this.fbEnvs,
        ...this.ggEnvs
    ];

    public static byProjectId(projectId: string): Env[] {
        if (projectId === ProjectMock.datamaintainProject.id) {
            return EnvMock.dmEnvs;
        }

        if (projectId === ProjectMock.githubProject.id) {
            return EnvMock.ghEnvs;
        }
        if (projectId === ProjectMock.facebookProject.id) {
            return EnvMock.fbEnvs;
        }

        if (projectId === ProjectMock.googleProject.id) {
            return EnvMock.ggEnvs;
        }

        return [];
    }
}
