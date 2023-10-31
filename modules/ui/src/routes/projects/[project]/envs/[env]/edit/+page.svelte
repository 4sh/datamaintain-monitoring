<script lang="ts">
    import {EnvService} from "$lib/services/EnvService";
    import {page} from "$app/stores";
    import {ProjectService} from "$lib/services/ProjectService";
    import M_error from "$lib/components/molecules/M_error.svelte";
    import {t} from "$lib/services/I18nService";
    import O_environmentEdition from "$lib/components/organisms/O_environment/O_environmentEdition.svelte";

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
{:then environment}
    {$t('environment.page.edition', {name: environment.name, projectName: project.name})}

    <O_environmentEdition projectRef="{project.id}" {environment}/>
{:catch error}
    <M_error>
        Env not found !
    </M_error>
{/await}
