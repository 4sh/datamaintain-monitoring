<script lang="ts">
    import {ModuleEnvMatrix} from "../../domain/ModuleEnvMatrix";
    import ExecutionMetadataComponent from "./ExecutionMetadataComponent.svelte";

    export let projectRef: string
    export let matrix: ModuleEnvMatrix
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
                <a href="/projects/{projectRef}/modules/{entry.moduleId}">{entry.moduleName}</a>
            </td>

            {#each matrix.envs as { id, name }}
                <td class="px-2">
                    <ExecutionMetadataComponent metadata="{entry.getEnvExecutionEntryMetadata(id)?.execution}"/>
                </td>
            {/each}
        </tr>
    {/each}
    </tbody>
</table>