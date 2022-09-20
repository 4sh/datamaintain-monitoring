import type {Env} from "../domain/Env";


export class EnvMock {
    public static readonly dmEnvs = [
        {id: 'dm-qa', name: "QA"},
        {id: 'dm-pp', name: "Preprod"},
        {id: 'dm-p', name: "Prod"}
    ];

    public static readonly ghEnvs = [
        {id: 'dm-qa', name: "QA"},
        {id: 'dm-pp', name: "Preprod"},
        {id: 'dm-p', name: "Prod"}
    ];

    public static readonly fbEnvs = [
        { id: 'fb-p', name: "Prod"}
    ];

    public static readonly ggEnvs = [
        { id: 'gg-pp', name: "Qualification"},
        { id: 'gg-p', name: "Prod"}
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
