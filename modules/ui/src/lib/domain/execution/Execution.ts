import {Type} from 'class-transformer';
import {Script} from '../script/Script';

export class Execution {
    id: string
    @Type(() => Date)
    startDate: Date
    @Type(() => Date)
    endDate: Date
    origin: ExecutionOrigin
    type: ExecutionType
    status: ExecutionStatus
    duration: number


    constructor(id: string, startDate: Date, endDate: Date, origin: ExecutionOrigin, type: ExecutionType, status: ExecutionStatus, duration: number) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.origin = origin;
        this.type = type;
        this.status = status;
        this.duration = duration;
    }
}

export class ScriptExecutionDetail {
    id: string
    @Type(() => Date)
    startDate: Date
    @Type(() => Date)
    endDate: Date
    durationInMs: number
    executionOrderIndex: number
    output: string
    status: ExecutionStatus

    @Type(() => Script)
    script: Script

    constructor(id: string, startDate: Date, endDate: Date, durationInMs: number, executionOrderIndex: number, output: string, status: ExecutionStatus, script: Script) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.durationInMs = durationInMs;
        this.executionOrderIndex = executionOrderIndex;
        this.output = output;
        this.status = status;
        this.script = script;
    }
}

export class ExecutionForDashboard extends Execution {
    project: {id: string, name: string, smallName: string}
    module: {id: string, name: string}
    env: {id: string, name: string, smallName: string}
    nbScriptsKO: number
    nbScriptsOK: number


    constructor(id: string, date: Date, origin: ExecutionOrigin, type: ExecutionType, status: ExecutionStatus, duration: number, project: {
        id: string;
        name: string;
        smallName: string
    }, module: { id: string; name: string }, env: {
        id: string;
        name: string;
        smallName: string
    }, nbScriptsKO: number, nbScriptsOK: number) {
        super(id, date, origin, type, status, duration);
        this.project = project;
        this.module = module;
        this.env = env;
        this.nbScriptsKO = nbScriptsKO;
        this.nbScriptsOK = nbScriptsOK;
    }
}

export class ExecutionDetail extends Execution {
    project: {name: string}
    module: {name: string}
    environment: {name: string}

    @Type(() => ScriptExecutionDetail)
    scriptsExecutions: ScriptExecutionDetail[]

    constructor(id: string, startDate: Date, endDate: Date, origin: ExecutionOrigin, type: ExecutionType, status: ExecutionStatus, duration: number, project: {
        name: string
    }, module: { name: string }, environment: { name: string }, scriptsExecutions: ScriptExecutionDetail[]) {
        super(id, startDate, endDate, origin, type, status, duration);
        this.project = project;
        this.module = module;
        this.environment = environment;
        this.scriptsExecutions = scriptsExecutions;
    }
}

export enum ExecutionOrigin {
    CLIENT = "CLIENT",
    SERVER = "SERVER",
    TIER = "TIER"
}

export enum ExecutionStatus {
    PLANNED = "PLANNED",
    IN_PROGRESS = "IN_PROGRESS",
    COMPLETED = "COMPLETED",
    ERROR = "ERROR"
}

export enum ExecutionType {
    ON_DEMAND = "ON_DEMAND",
    PLANNED = "PLANNED",
    TEST = "TEST"
}
