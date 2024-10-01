<script lang="ts">
    import type {ModuleEnvMatrix} from "$lib/domain/ModuleEnvMatrix";
    import O_matrixExecutionMetadata from "$lib/components/organisms/O_matrix/O_matrixExecutionMetadata.svelte";
    import M_table from '$lib/components/molecules/table/M_table.svelte';
    import M_tableHeaderItem from '$lib/components/molecules/table/M_tableHeaderItem.svelte';
    import M_tableBodyItem from '$lib/components/molecules/table/M_tableBodyItem.svelte';

    export let projectRef: string
    export let matrix: ModuleEnvMatrix
</script>

<M_table>
    <svelte:fragment slot="headerContent">
        <M_tableHeaderItem>
            Nom du module
        </M_tableHeaderItem>
        {#each matrix.envs as { id, name }}
            <M_tableHeaderItem><a href="/projects/{projectRef}/envs/{id}">{name}</a></M_tableHeaderItem>
        {/each}
    </svelte:fragment>
    <svelte:fragment slot="bodyContent">
        {#each matrix.entries as entry}

            <tr>
                <M_tableBodyItem>
                <a href="/projects/{projectRef}/modules/{entry.moduleId}">{entry.moduleName}</a>
                </M_tableBodyItem>

            {#each matrix.envs as { id, name }}
                <M_tableBodyItem>
                    <O_matrixExecutionMetadata metadata="{entry.getEnvExecutionEntryMetadata(id)?.execution}"/>
                </M_tableBodyItem>
            {/each}
        </tr>
        {/each}
    </svelte:fragment>
</M_table>