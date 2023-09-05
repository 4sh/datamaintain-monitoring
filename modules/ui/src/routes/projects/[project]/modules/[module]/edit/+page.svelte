<script lang="ts">
    import {page} from "$app/stores";
    import {ProjectService} from "$lib/services/ProjectService";
    import {ModuleService} from "$lib/services/ModuleService";
    import {EnvService} from "$lib/services/EnvService";
    import {ExecutionMock} from "$lib/mocks/ExecutionMock";
    import M_error from "$lib/components/molecules/M_error.svelte";

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
    Edition du module {module.name} du projet {project.name}
{:catch error}
    <M_error>
        Module not found !
    </M_error>
{/await}
