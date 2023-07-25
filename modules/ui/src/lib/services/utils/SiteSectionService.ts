import {writable} from "svelte/store";

export enum SiteSectionType {
    DASHBOARD = "DASHBOARD",
    PROJECT = "PROJECT",
    MODULE = "MODULE",
    ENV = "ENV",
    HISTORY = "HISTORY",

    NONE = "NONE" // by default value if no section found
}

export class SiteSection {
    public type: SiteSectionType
    public context: Array<{name: string, value: string}> = []

    constructor(type: SiteSectionType, context: Array<{ name: string; value: string }> = []) {
        this.type = type;
        this.context = context;
    }
}

export class SiteSectionService {
    private static PROJECT_REGEX = /^\/projects\/([^\/]*)$/;
    private static ENV_REGEX = /^\/projects\/([^\/]*)\/envs\/([^\/]*)$/;
    private static MODULE_REGEX = /^\/projects\/([^\/]*)\/modules\/([^\/]*)$/;
    private static MODULE_ENV_REGEX = /^\/projects\/([^\/]*)\/modules\/([^\/]*)\?env=([^\/]*)$/;

    public static toSiteSection(url: URL | string): SiteSection {
        let urlString;

        if (typeof url === 'string') {
            urlString = url;
        } else {
            urlString = url.pathname;
        }

        if (urlString === "/dashboard") {
            return new SiteSection(SiteSectionType.DASHBOARD);
        }

        if (urlString === "/history") {
            return new SiteSection(SiteSectionType.HISTORY);
        }

        const projectRegexMatch = urlString.match(this.PROJECT_REGEX);
        if (projectRegexMatch) {
            return new SiteSection(
                SiteSectionType.PROJECT,
                [
                    {name: "project", value: projectRegexMatch[1]}
                ]
            );
        }

        const envRegexMatch = urlString.match(this.ENV_REGEX);
        if (envRegexMatch) {
            return new SiteSection(
                SiteSectionType.ENV,
                [
                    {name: "project", value: envRegexMatch[1]},
                    {name: "env", value: envRegexMatch[2]}
                ]
            );
        }

        const moduleRegexMatch = urlString.match(this.MODULE_REGEX);
        if (moduleRegexMatch && typeof url !== 'string') {
            urlString += url.search;
        }

        const moduleEnvRegexMatch = urlString.match(this.MODULE_ENV_REGEX);
        if (moduleEnvRegexMatch) {
            return new SiteSection(
                SiteSectionType.MODULE,
                [
                    {name: "project", value: moduleEnvRegexMatch[1]},
                    {name: "module", value: moduleEnvRegexMatch[2]},
                    {name: "env", value: moduleEnvRegexMatch[3]}
                ]
            );
        }

        return new SiteSection(SiteSectionType.NONE);
    }

    public static isParentOf(parentSection: SiteSection, childSection: SiteSection) {
        switch (childSection.type) {
            case SiteSectionType.ENV:
                return parentSection.type === SiteSectionType.PROJECT
                    && this.haveSameProject(childSection, parentSection)
            case SiteSectionType.MODULE:
                return (parentSection.type === SiteSectionType.PROJECT
                        && this.haveSameProject(childSection, parentSection))
                    || (parentSection.type === SiteSectionType.ENV
                        && this.haveSameProject(childSection, parentSection)
                        && this.haveSameEnv(childSection, parentSection)
                    )
            default:
                return false
        }
    }

    private static haveSameProject(siteSection: SiteSection, otherSiteSection: SiteSection) {
        return this.extractProject(siteSection) === this.extractProject(otherSiteSection);
    }

    private static haveSameEnv(siteSection: SiteSection, otherSiteSection: SiteSection) {
        return this.extractEnv(siteSection) === this.extractEnv(otherSiteSection);
    }

    private static extractProject(siteSection: SiteSection) {
        return this.extractValue(siteSection, 'project');
    }

    private static extractEnv(siteSection: SiteSection) {
        return this.extractValue(siteSection, 'env');
    }

    private static extractValue(siteSection: SiteSection, type: string) {
        const values = siteSection.context
            .filter(item => item.name === type)
            .map(item => item.value);

        return values && values[0];
    }
}

export const currentSiteSection = writable(new SiteSection(SiteSectionType.NONE));