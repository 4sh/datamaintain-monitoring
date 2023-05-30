<script lang="ts">
    import M_inputText from "$lib/components/molecules/M_inputText.svelte";

    export let project = {}
</script>

<form>
    <M_inputText label="Name" bind:value={project.name} />
</form>

<style>
</style>
