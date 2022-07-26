<script lang="ts">
    export let executions;
</script>

<h1>Lsite des exécutions</h1>
<label>{executions.length} exécution(s)</label>
<body>
    <ul>
        {#each executions as execution}
            <li><a href="/executions/{execution.id}/detail">{execution.id} - {execution.date} - {execution.origin} - {execution.status}</a></li>
        {/each}
    </ul>
</body>


<style>
</style>
