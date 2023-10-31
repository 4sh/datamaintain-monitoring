<script lang="ts">
    import { t } from '$lib/services/I18nService';
    import {page} from "$app/stores";
    import O_environmentEdition from "$lib/components/organisms/O_environment/O_environmentEdition.svelte";

    let environment = {};
    let projectRef;

    $: if($page.params?.project) {
        projectRef = $page.params.project;
    }
</script>

{$t('environment.page.creation')}

<O_environmentEdition {projectRef} {environment} />
