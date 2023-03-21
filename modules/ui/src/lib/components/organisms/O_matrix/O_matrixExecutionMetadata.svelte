<script lang="ts">
    import {ExecutionMetadata} from "../../../domain/ExecutionMetadata";

    export let metadata: ExecutionMetadata
</script>

{#if metadata}
    {metadata.status}
    <div><a href="/executions/{metadata.executionId}">{metadata.executionDate.toLocaleDateString()}</a></div>
    <div>{metadata.duration}</div>
{/if}
