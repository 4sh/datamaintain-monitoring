<script lang="ts">
    import {page} from "$app/stores";
    import {ProjectService} from "$lib/services/ProjectService";
    import {ModuleService} from "$lib/services/ModuleService";
    import {EnvService} from "$lib/services/EnvService";
    import {ExecutionMock} from "$lib/mocks/ExecutionMock";
    import M_error from "$lib/components/molecules/M_error.svelte";
    import {t} from "$lib/services/I18nService";
    import O_moduleEdition from "$lib/components/organisms/O_module/O_moduleEdition.svelte";
    import {ExecutionService} from '$lib/services/ExecutionService';

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

    $: if ($page.params?.module && project) {
        ExecutionService.scriptEnvMatrixByProjectAndModule(project.id, $page.params?.module)
            .then(res => scriptEnvMatrix = res);
    }

</script>

{#await modulePromise}
    <p>...waiting</p>
{:then module}
    {$t('module.page.edition', {name: module.name, projectName: project.name})}

    <O_moduleEdition projectRef="{project.id}" {module}/>
{:catch error}
    <M_error>
        Module not found !
    </M_error>
{/await}
