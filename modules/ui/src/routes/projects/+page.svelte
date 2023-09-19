<script lang="ts">
    import O_projectEdition from "$lib/components/organisms/O_project/O_projectEdition.svelte";
    import { t } from '$lib/services/I18nService';

    let project = {};
</script>

{$t('project.page.creation')}

<O_projectEdition {project} />