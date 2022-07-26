<script lang="ts">
    export let execution;
</script>

<h1>Détail de l'exécution</h1>
<body>
    <h2>Infos</h2>
    <p>Date: {execution.date} Status: {execution.status} Origine: {execution.origin}</p>
    <h2>Scan:</h2>
    <p>{execution.report.scannedScripts.length} script(s) scanné(s)</p>
    <ul>
        {#each execution.report.scannedScripts as scannedScript}
            <li>{scannedScript}</li>
        {/each}
    </ul>

    <h2>Filtrage:</h2>
    <p>{execution.report.filteredScripts.length} script(s) filtré(s)</p>
    <ul>
        {#each execution.report.filteredScripts as filteredScript}
            <li>{filteredScript}</li>
        {/each}
    </ul>

    <h2>Prunned:</h2>
    <p>{execution.report.prunedScripts.length} script(s) pruned</p>
    <ul>
        {#each execution.report.prunedScripts as prunedScript}
            <li>{prunedScript}</li>
        {/each}
    </ul>

    <h2>Exécuté:</h2>
    <p>{execution.report.executedScripts.length} script(s) exécuté(s)</p>
    <ul>
        {#each execution.report.prunedScripts as prunedScript}
            <li>{prunedScript}</li>
        {/each}
    </ul>
</body>


<style>
</style>
