<script lang="ts">
    import { t } from '$lib/services/I18nService';
    import O_moduleEdition from "$lib/components/organisms/O_module/O_moduleEdition.svelte";
    import {page} from "$app/stores";

    let module = {};
    let projectRef;

    $: if($page.params?.project) {
        projectRef = $page.params.project;
    }
</script>

{$t('module.page.creation')}

<O_moduleEdition {projectRef} {module} />