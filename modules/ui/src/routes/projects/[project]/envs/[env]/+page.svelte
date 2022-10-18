<script lang="ts">
    import {EnvService} from "../../../../../lib/services/EnvService";
    import {page} from "$app/stores";
    import {ProjectService} from "../../../../../lib/services/ProjectService";

    let project
    let env

    $: if($page.params?.project) {
        project = ProjectService.byId($page.params.project);
    }

    $: if($page.params?.env) {
        env = EnvService.byId($page.params.env);
    }
</script>

Environnement {env.name} du projet {project.name}
