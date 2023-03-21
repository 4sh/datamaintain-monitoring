import type {ExecutedScript, Script} from "$lib/domain/script/Script";
import {ScriptAction} from "$lib/domain/script/Script";
import type {ExecutionStatus} from "$lib/domain/execution/Execution";


const script1 = {
    name: "1-script1-add_something",
    checksum: "abcde",
    identifier: "1-script1",
    tags: new Set( [{name:"tag1"}] ),
    action: ScriptAction.RUN,
    porcelainName: "1-script1"
};

const script2 = {
    name: "2-script2-do_something_else",
    checksum: "bcdef",
    identifier: "2-script2",
    tags: new Set( [{name:"tag1"}] ),
    action: ScriptAction.RUN,
    porcelainName: "2-script2"
};

const script3 = {
    name: "3-script3-reset_something",
    checksum: "cdefg",
    identifier: "3-script3",
    tags: new Set( [{name:"tag1"}, {name:"tag2"}] ),
    action: ScriptAction.RUN,
    porcelainName: "3-script3"
};

const script4 = {
    name: "4-script4-update_data_somewhere",
    checksum: "defgh",
    identifier: "4-script4",
    tags: new Set( [{name:"tag1"}] ),
    action: ScriptAction.RUN,
    porcelainName: "4-script4"
};

export class ScriptMock {
    public static readonly scripts: Script[] = [
        script1,
        script2,
        script3,
        script4
    ];

    public static toExecutedScript(script: Script, executionStatus: ExecutionStatus, duration: number, flags: string[]): ExecutedScript {
        return {
            ...script,
            executionStatus,
            executionDurationInMillis: duration,
            flags
        }
    }
}
