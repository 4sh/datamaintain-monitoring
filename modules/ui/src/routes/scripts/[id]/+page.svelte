<script lang="ts">
    import type {Script} from '$lib/domain/script/Script';
    import {page} from '$app/stores';
    import {ScriptService} from '$lib/services/ScriptService';

    let scriptPromise: Promise<Script>
    $: if($page.params?.id) {
        scriptPromise = ScriptService.byId($page.params.id)
    }

</script>

Script [id]

{#await scriptPromise}
    <p>...waiting</p>
{:then script}

    {script.name}
{/await}