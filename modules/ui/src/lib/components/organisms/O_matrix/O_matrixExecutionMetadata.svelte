<script lang="ts">
    import {ExecutionMetadata} from "$lib/domain/ExecutionMetadata";

    export let metadata: ExecutionMetadata
</script>

{#if metadata}
    {metadata.status}
    {#if metadata.executionDate}
        <div><a href="/executions/{metadata.executionId}">{metadata.executionDate.toLocaleDateString()}</a></div>
    {/if}
    <div>{metadata.duration}</div>
{/if}
