import type {ExecutionReport} from "./ExecutionReport";
import type {Origin} from "./Origin";
import type {ExecutionType} from "./ExecutionType";
import type {ExecutionStatus} from "./ExecutionStatus";

export interface Execution {
    id: string,
    date: Date,
    origin: Origin,
    type: ExecutionType,
    status: ExecutionStatus,
    report: ExecutionReport
}
