<script lang="ts">
    import type {ScriptExecutionMetadata} from "$lib/domain/ScriptExecutionMetadata";

    export let metadata: ScriptExecutionMetadata
</script>

{#if metadata}
    <div><a href="/executions/{metadata.executionId}">{metadata.executionDate.toLocaleDateString()}</a></div>
    <div><a href="/executions/{metadata.executionId}/scripts/{metadata.scriptId}">{metadata.duration}</a></div>
{/if}
