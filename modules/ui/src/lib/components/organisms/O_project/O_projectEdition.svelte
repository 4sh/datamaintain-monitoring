<script lang="ts">
    import M_inputText from "$lib/components/molecules/M_inputText.svelte";
    import A_button from "$lib/components/atoms/A_button.svelte";
    import {ProjectService} from "$lib/services/ProjectService";
    import type {Project} from "$lib/domain/Project";

    export let project: Project = {} as Project;

    function save() {
        if (project.id) {
            ProjectService.updateName(project);
        } else {
            ProjectService.create(project);
        }
    }
</script>

<form on:submit|preventDefault={save}>
    <M_inputText label="Name" bind:value={project.name} required="true" placeholder="Name..."/>
    <M_inputText label="Small name" bind:value={project.smallName} required="true" placeholder="Small name..."/>

    <A_button label='Sauvegarder' type="submit"/>
</form>

<style>
</style>
