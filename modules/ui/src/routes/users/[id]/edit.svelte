<script context="module">
    import { page } from '$app/stores';
</script>
<script>
    import UserEdition from "../../../lib/components/user/UserEdition.svelte";
</script>

<UserEdition userId="{$page.params.id}"/>