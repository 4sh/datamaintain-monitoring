export interface ExecutionReport {
    scannedScripts: string[]
    filteredScripts: string[]
    prunedScripts: string[]
    executedScripts: string[]
    validatedCheckRules: string[]
}
