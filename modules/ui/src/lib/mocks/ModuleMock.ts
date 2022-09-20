import type {Module} from "../domain/Module";


export class ModuleMock {
    public static readonly dmModules = [
        { id: 'dm-dm', name: "Datamaintain"}
    ];

    public static readonly ghModules = [
        { id: 'gh-gp', name: "GithubPages"},
        { id: 'gh-pr', name: "PRs"}
    ];

    public static readonly fbModules = [
        { id: 'fb-fb', name: "Facebook"},
        { id: 'fg-ig', name: "Instagram"}
    ];

    public static readonly ggModules = [
        { id: 'gg-yt', name: "YouTube"},
        { id: 'gg-gp', name: "Google Play"},
        { id: 'gg-gs', name: "Google Store"},
        { id: 'gg-ag', name: "ApiGee"}
    ];

    public static readonly modules: Module[] = [
        ...this.dmModules,
        ...this.ghModules,
        ...this.fbModules,
        ...this.ggModules
    ];

    public static byProjectId(projectId: string): Module[] {
        return this[projectId + "Modules" as keyof typeof ModuleMock] as Module[];
    }

    public static byProjectIdAndEnv(projectId: string, envId: string): Module[] {
        switch (projectId) {
            case 'dm':
                switch (envId) {
                    case 'dm-qa': return this.dmModules;
                    case 'dm-pp': return this.dmModules;
                    case 'dm-p': return this.dmModules;
                    default: return [];
                }
            case 'gh':
                switch (envId) {
                    case 'gh-qa': return this.ghModules;
                    case 'gh-pp': return this.ghModules;
                    case 'gh-p': return [
                        this.ghModules[0]
                    ];
                    default: return [];
                }
            case 'fb':
                switch (envId) {
                    case 'f-p': return this.fbModules;
                    default: return [];
                }
            case 'gg':
                switch (envId) {
                    case 'gg-pp': return [
                        this.ggModules[0],
                        this.ggModules[1],
                        this.ggModules[2],
                    ];
                    case 'gg-p': return [
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
