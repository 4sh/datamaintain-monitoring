import type {Tag} from "../Tag";
import type {ExecutionStatus} from "../execution/Execution";

export class Script {
    name: string
    checksum: string
    identifier: string
    tags: Set<Tag>
    action: ScriptAction
    porcelainName: string

    constructor(name: string, checksum: string, identifier: string, tags: Set<Tag>, action: ScriptAction, porcelainName: string) {
        this.name = name;
        this.checksum = checksum;
        this.identifier = identifier;
        this.tags = tags;
        this.action = action;
        this.porcelainName = porcelainName;
    }
}

export interface ExecutedScript extends Script {
    executionStatus: ExecutionStatus,
    executionDurationInMillis: number,
    flags: string[],
}

export enum ScriptAction {
    RUN = "RUN",
    MARK_AS_EXECUTED = "MARK_AS_EXECUTED",
    OVERRIDE_EXECUTED = "OVERRIDE_EXECUTED"
}