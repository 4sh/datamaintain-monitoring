<script lang="ts">
    import {ProjectService} from "$lib/services/ProjectService";
    import {page} from "$app/stores";

    let projectPromise

    $: if($page.params?.project) {
        projectPromise = ProjectService.byId($page.params.project);
    }
</script>

{#await projectPromise}
    <p>...waiting</p>
{:then project}
    Edition du project {project.name}
{:catch error}
    <p style="color: red">Project not found !</p>
{/await}