<script lang="ts">
    import M_inputText from "$lib/components/molecules/M_inputText.svelte";
    import A_button from "$lib/components/atoms/A_button.svelte";
    import {t} from "$lib/services/I18nService";
    import type {Module} from "$lib/domain/Module";
    import {ModuleService} from "$lib/services/ModuleService";

    export let module: Module = {} as Module;
    export let projectRef;

    function save() {
        if (module.id) {
            ModuleService.updateName(module);
        } else {
            ModuleService.create(projectRef, module);
        }
    }
</script>

<form on:submit|preventDefault={save}>
    <M_inputText label="{$t('module.name')}" bind:value={module.name} required="true" placeholder="{$t('module.name')}"/>

    <A_button label="{$t('common.actions.save')}" type="submit"/>
</form>

<style>
</style>
