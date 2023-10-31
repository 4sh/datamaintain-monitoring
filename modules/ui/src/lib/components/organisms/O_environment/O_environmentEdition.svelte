<script lang="ts">
    import M_inputText from "$lib/components/molecules/M_inputText.svelte";
    import A_button from "$lib/components/atoms/A_button.svelte";
    import {t} from "$lib/services/I18nService";
    import type {Env} from "$lib/domain/Env";
    import {EnvService} from "$lib/services/EnvService";

    export let environment: Env = {} as Env;
    export let projectRef;

    function save() {
        if (environment.id) {
            EnvService.updateName(environment);
        } else {
            EnvService.create(projectRef, environment);
        }
    }
</script>

<form on:submit|preventDefault={save}>
    <M_inputText label="{$t('environment.name')}" bind:value={environment.name} required="true" placeholder="{$t('environment.name')}"/>
    <M_inputText label="{$t('project.smallName')}" bind:value={environment.smallName} required="true" placeholder="{$t('environment.smallName')}"/>

    <A_button label="{$t('common.actions.save')}" type="submit"/>
</form>

<style>
</style>
