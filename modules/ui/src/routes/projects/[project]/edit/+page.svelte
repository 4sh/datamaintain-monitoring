<script lang="ts">
    import {ProjectService} from "$lib/services/ProjectService";
    import {page} from "$app/stores";
    import O_projectEdition from "$lib/components/organisms/O_project/O_projectEdition.svelte";
    import M_error from "$lib/components/molecules/M_error.svelte";
    import {t} from "$lib/services/I18nService";

    let projectPromise

    $: if($page.params?.project) {
        projectPromise = ProjectService.byId($page.params.project);
    }
</script>

{#await projectPromise}
    <p>...waiting</p>
{:then project}
    {$t('project.page.edition', { name: project.name })}


    <O_projectEdition {project} />

{:catch error}
    <M_error>
        Project not found !
    </M_error>
{/await}
