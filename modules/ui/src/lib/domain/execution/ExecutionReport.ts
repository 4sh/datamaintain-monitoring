import type {ExecutedScript, Script} from "../script/Script";

export interface ExecutionReport {
    scannedScripts: Script[]
    filteredScripts: Script[]
    prunedScripts: Script[]
    executedScripts: ExecutedScript[]
    validatedCheckRules: string[]
}


