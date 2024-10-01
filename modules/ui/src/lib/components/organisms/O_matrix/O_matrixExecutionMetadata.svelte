<script lang="ts">
    import {ExecutionMetadata} from "$lib/domain/ExecutionMetadata";
    import M_executionStatusIcon from '$lib/components/molecules/M_executionStatusIcon.svelte';

    export let metadata: ExecutionMetadata
</script>

{#if metadata}
    <M_executionStatusIcon executionStatus="{metadata.status}"></M_executionStatusIcon>
    {#if metadata.executionDate}
        <div><a href="/executions/{metadata.executionId}">{metadata.executionDate.toLocaleDateString()}</a></div>
    {/if}
    <div>{metadata.duration}</div>
{/if}
