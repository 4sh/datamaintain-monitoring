import type {Env} from "$lib/domain/Env";


export class EnvMock {
    public static readonly dmEnvs = [
        {id: 'dm-qa', name: "QA", smallName: "Qa"},
        {id: 'dm-pp', name: "Preprod", smallName: "Pp"},
        {id: 'dm-p', name: "Prod", smallName: "P"}
    ];

    public static readonly ghEnvs = [
        {id: 'gh-qa', name: "QA", smallName: "Qa"},
        {id: 'gh-pp', name: "Preprod", smallName: "Pp"},
        {id: 'gh-p', name: "Prod", smallName: "P"}
    ];

    public static readonly fbEnvs = [
        { id: 'fb-p', name: "Prod", smallName: "P"}
    ];

    public static readonly ggEnvs = [
        { id: 'gg-pp', name: "Qualification", smallName: "Qa"},
        { id: 'gg-p', name: "Prod", smallName: "P"}
    ];

    public static readonly envs: Env[] = [
        ...this.dmEnvs,
        ...this.ghEnvs,
        ...this.fbEnvs,
        ...this.ggEnvs
    ];

    public static byProjectId(projectId: string): Env[] {
        return this[projectId + "Envs" as keyof typeof EnvMock] as Env[];
    }
}
