<script lang="ts">
    import {page} from "$app/stores";
    import {ProjectService} from "$lib/services/ProjectService";
    import {ModuleService} from "$lib/services/ModuleService";
    import {EnvService} from "$lib/services/EnvService";
    import {ExecutionMock} from "$lib/mocks/ExecutionMock";
    import O_matrixScriptEnv from "$lib/components/organisms/O_matrix/O_matrixScriptEnv.svelte";

    let project
    let env
    let modulePromise
    let scriptEnvMatrix

    $: if($page.params?.project) {
        ProjectService.byId($page.params.project).then(foundProject => {
            project = foundProject
        });
    }

    $: if($page.url.searchParams?.has('env')) {
        EnvService.byId($page.url.searchParams?.get('env')).then(foundEnv => {
            env = foundEnv
        });
    }

    $: if($page.params?.module) {
        modulePromise = ModuleService.byId($page.params.module);
    }

    $: if (project) {
        scriptEnvMatrix = ExecutionMock.scriptEnvMatrixByProject(project.id)
    }
</script>

{#await modulePromise}
    <p>...waiting</p>
{:then module}
    Module {module.name} du projet {project.name} {#if env} sur l'environnement {env.name}{/if}

    <br>
    <a href="{module.id}/edit">Editer</a>

    {#if scriptEnvMatrix}
        <O_matrixScriptEnv projectRef="{project.id}" matrix="{scriptEnvMatrix}"></O_matrixScriptEnv>
    {/if}
{:catch error}
    <p style="color: red">Module not found !</p>
{/await}