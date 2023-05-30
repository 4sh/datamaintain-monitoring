<script lang="ts">
    import M_inputText from "$lib/components/molecules/M_inputText.svelte";
    import A_button from "$lib/components/atoms/A_button.svelte";

    export let project = {}

    function save() {
        // TODO
    }
</script>

<form on:submit|preventDefault={save}>
    <M_inputText label="Name" bind:value={project.name} required="true" placeholder="Name..."/>

    <A_button label='Sauvegarder' type="submit"/>
</form>

<style>
</style>
