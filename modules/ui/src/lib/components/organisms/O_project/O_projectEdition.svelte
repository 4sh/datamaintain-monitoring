<script lang="ts">
    import M_inputText from "$lib/components/molecules/M_inputText.svelte";
    import A_button from "$lib/components/atoms/A_button.svelte";
    import {ProjectService} from "$lib/services/ProjectService";
    import type {Project} from "$lib/domain/Project";
    import {t} from "$lib/services/I18nService";

    export const prerender = false;
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
    <M_inputText label="{$t('project.name')}" bind:value={project.name} required="true" placeholder="{$t('project.name')}" testId="name"/>
    <M_inputText label="{$t('project.smallName')}" bind:value={project.smallName} required="true" placeholder="{$t('project.smallName')}" testId="smallName"/>

    <A_button label="{$t('common.actions.save')}" type="submit"/>
</form>

<style>
</style>
