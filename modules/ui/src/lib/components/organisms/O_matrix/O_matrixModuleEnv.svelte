<script lang="ts">
    import {ModuleEnvMatrix} from "$lib/domain/ModuleEnvMatrix";
    import O_matrixExecutionMetadata from "$lib/components/organisms/O_matrix/O_matrixExecutionMetadata.svelte";

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
                    <O_matrixExecutionMetadata metadata="{entry.getEnvExecutionEntryMetadata(id)?.execution}"/>
                </td>
            {/each}
        </tr>
    {/each}
    </tbody>
</table>
