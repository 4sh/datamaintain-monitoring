<script lang="ts">
    import {page} from "$app/stores";
    import {ProjectService} from "$lib/services/ProjectService";
    import {ModuleService} from "$lib/services/ModuleService";
    import {EnvService} from "$lib/services/EnvService";
    import {ExecutionMock} from "$lib/mocks/ExecutionMock";
    import O_matrixScriptEnv from "$lib/components/organisms/O_matrix/O_matrixScriptEnv.svelte";
    import M_error from "$lib/components/molecules/M_error.svelte";
    import {ModuleEnvironmentTokenService} from '$lib/services/ModuleEnvironmentTokenService';
    import type {ModuleEnvironmentToken} from '$lib/domain/ModuleEnvironmentToken';
    import A_button from '$lib/components/atoms/A_button.svelte';

    let project
    let env
    let modulePromise
    let scriptEnvMatrix
    let moduleEnvironmentTokenPromise: Promise<ModuleEnvironmentToken>

    $: if($page.params?.project) {
        ProjectService.byId($page.params.project).then(foundProject => {
            project = foundProject
        });
    }

    const environmentRef = $page.url.searchParams?.get('env');
    $: if(environmentRef) {
        EnvService.byId(environmentRef).then(foundEnv => {
            env = foundEnv
        });
    }

    const moduleRef = $page.params?.module;
    $: if(moduleRef) {
        modulePromise = ModuleService.byId($page.params.module);
    }

    $: if(environmentRef && moduleRef) {
        moduleEnvironmentTokenPromise = ModuleEnvironmentTokenService.byModuleAndEnvironment(
            moduleRef,
            environmentRef
        )
    }

    $: if (project) {
        scriptEnvMatrix = ExecutionMock.scriptEnvMatrixByProject(project.id)
    }

    export function regenerateToken() {
        console.log('pouet');
        moduleEnvironmentTokenPromise = ModuleEnvironmentTokenService.regenerateToken(
            moduleRef,
            environmentRef
        )
    }
</script>

{#await modulePromise}
    <p>...waiting</p>
{:then module}
    Module {module.name} du projet {#if project} {project.name} {/if} {#if env} sur l'environnement {env.name}{/if}

    {#await moduleEnvironmentTokenPromise}
        Chargement du token en cours...
    {:then moduleEnvironmentToken}
        Token : {moduleEnvironmentToken.tokenValue}
        <A_button label="Régénérer token" onClickAction={regenerateToken}>
        </A_button>
    {/await}

    <br>
    <a href="{module.id}/edit">Editer</a>

    {#if scriptEnvMatrix}
        <O_matrixScriptEnv projectRef="{project.id}" matrix="{scriptEnvMatrix}"></O_matrixScriptEnv>
    {/if}
{:catch error}
    <M_error>
        Module not found !
    </M_error>
{/await}
