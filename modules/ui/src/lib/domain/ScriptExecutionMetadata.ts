import {Type} from 'class-transformer';

export class ScriptExecutionMetadata {
    executionId: string
    @Type(() => Date)
    executionDate: Date
    duration: number
    scriptId: string | null = null

    constructor(executionId: string, executionDate: Date, duration: number, scriptId: string | null = null) {
        this.executionId = executionId;
        this.executionDate = executionDate;
        this.duration = duration;
        this.scriptId = scriptId;
    }
}