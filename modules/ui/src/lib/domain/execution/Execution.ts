import type {ExecutionReport} from "./ExecutionReport";

export interface Execution {
    id: string,
    date: Date,
    origin: ExecutionOrigin,
    type: ExecutionType,
    status: ExecutionStatus,
    duration: number
}

export interface ExecutionWithReport extends Execution {
    report: ExecutionReport
}

export interface ExecutionForDashboard extends Execution {
    project: {id: string, name: string, smallName: string},
    module: {id: string, name: string},
    env: {id: string, name: string, smallName: string},
    nbScriptsKO: number,
    nbScriptsOK: number
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
