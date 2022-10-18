<script lang="ts">
    import {page} from "$app/stores";
    import {ProjectService} from "../../../../../lib/services/ProjectService";
    import {ModuleService} from "../../../../../lib/services/ModuleService";
    import {EnvService} from "../../../../../lib/services/EnvService";

    let project
    let env
    let module

    $: if($page.params?.project) {
        project = ProjectService.byId($page.params.project);
    }

    $: if($page.url.searchParams?.has('env')) {
        env = EnvService.byId($page.url.searchParams?.get('env'));
    }

    $: if($page.params?.module) {
        module = ModuleService.byId($page.params.module);
    }
</script>

Module {module.name} du projet {project.name} {#if env} sur l'environnement {env.name}{/if}