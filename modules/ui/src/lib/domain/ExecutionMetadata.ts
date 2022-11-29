import type {ExecutionStatus} from "./execution/Execution";

export class ExecutionMetadata {
    executionId: string
    executionDate: Date
    duration: number
    status: ExecutionStatus

    constructor(executionId: string, executionDate: Date, duration: number, status: ExecutionStatus) {
        this.executionId = executionId;
        this.executionDate = executionDate;
        this.duration = duration;
        this.status = status;
    }
}