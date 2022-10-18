<script lang="ts">
    import {page} from "$app/stores";
    import {ProjectService} from "../../../../../lib/services/ProjectService";
    import {ModuleService} from "../../../../../lib/services/ModuleService";
    import {EnvService} from "../../../../../lib/services/EnvService";

    let project
    let env
    let modulePromise

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
</script>

{#await modulePromise}
    <p>...waiting</p>
{:then module}
    Module {module.name} du projet {project.name} {#if env} sur l'environnement {env.name}{/if}
{:catch error}
    <p style="color: red">Module not found !</p>
{/await}