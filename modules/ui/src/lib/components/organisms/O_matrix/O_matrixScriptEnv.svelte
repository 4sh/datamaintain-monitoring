<script lang="ts">
	import {ScriptEnvMatrix} from "../../../domain/script/ScriptEnvMatrix";
    import ScriptExecutionMetadataComponent from "./O_matrixScriptExecutionMetadata.svelte";

    export let projectRef: string
    export let matrix: ScriptEnvMatrix
</script>

<table class="table-auto">
    <thead>
    <tr>
        <th></th>
        {#each matrix.envs as { id, name }}
            <th><a href="/projects/{projectRef}/envs/{id}">{name}</a></th>
        {/each}
    </tr>
    </thead>
    <tbody>

    {#each matrix.entries as entry}
        <tr>
            <td>
                <a href="/scripts/{entry.scriptId}">{entry.scriptName}</a>
            </td>

            {#each matrix.envs as { id, name }}
                <td class="px-2">
                    <ScriptExecutionMetadataComponent metadata="{entry.getScriptExecutionMetadata(id)?.execution}"/>
                </td>
            {/each}
        </tr>
    {/each}
    </tbody>
</table>
