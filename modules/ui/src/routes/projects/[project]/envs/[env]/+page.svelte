<script lang="ts">
    import {EnvService} from "$lib/services/EnvService";
    import {page} from "$app/stores";
    import {ProjectService} from "$lib/services/ProjectService";

    let project
    let envPromise

    $: if($page.params?.project) {
        ProjectService.byId($page.params.project).then(foundProject => {
            project = foundProject
        });
    }

    $: if($page.params?.env) {
        envPromise = EnvService.byId($page.params.env);
    }
</script>

{#await envPromise}
    <p>...waiting</p>
{:then env}
    Environnement {env.name} du projet {project.name}

    <br>
    <a href="{env.id}/edit">Editer</a>
{:catch error}
    <p style="color: red">Env not found !</p>
{/await}