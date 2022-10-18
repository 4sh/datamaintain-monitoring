<script lang="ts">
    import {page} from '$app/stores';

    import {UserService} from "../../../../lib/services/UserService";
    import UserEdition from "../../../../lib/components/user/UserEdition.svelte";

    let userPromise

    $: if($page.params?.id) {
        userPromise = UserService.byId($page.params.id);
    }
</script>

{#await userPromise}
    <p>...waiting</p>
{:then user}
    <UserEdition {user}/>
{:catch error}
    <p style="color: red">User not found !</p>
{/await}