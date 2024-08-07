import {Type} from 'class-transformer';

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


    constructor(id: string, date: Date, origin: ExecutionOrigin, type: ExecutionType, status: ExecutionStatus, duration: number, project: {
        name: string
    }, module: { name: string }, environment: { name: string }) {
        super(id, date, origin, type, status, duration);
        this.project = project;
        this.module = module;
        this.environment = environment;
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
