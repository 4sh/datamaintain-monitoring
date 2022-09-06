import type {ExecutedScript, Script} from "../domain/script/Script";
import {ScriptAction} from "../domain/script/Script";
import type {ExecutionStatus} from "../domain/execution/Execution";


export class ScriptMock {
    public static readonly scripts: Script[] = [
        {
            name: "1-script1-add_something",
            checksum: "abcde",
            identifier: "1-script1",
            tags: new Set( [{name:"tag1"}] ),
            action: ScriptAction.RUN,
            porcelainName: "1-script1"
        },
        {
            name: "2-script2-do_something_else",
            checksum: "bcdef",
            identifier: "2-script2",
            tags: new Set( [{name:"tag1"}] ),
            action: ScriptAction.RUN,
            porcelainName: "2-script2"
        },
        {
            name: "3-script3-reset_something",
            checksum: "cdefg",
            identifier: "3-script3",
            tags: new Set( [{name:"tag1"}, {name:"tag2"}] ),
            action: ScriptAction.RUN,
            porcelainName: "3-script3"
        },
        {
            name: "4-script4-update_data_somewhere",
            checksum: "defgh",
            identifier: "4-script4",
            tags: new Set( [{name:"tag1"}] ),
            action: ScriptAction.RUN,
            porcelainName: "4-script4"
        }
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
