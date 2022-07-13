<script lang="ts">
    import {page} from '$app/stores';

    import {UserService} from "../../../lib/services/UserService";
    import UserEdition from "../../../lib/components/user/UserEdition.svelte";

    let user

    $: if($page.params?.id) {
        user = UserService.byId($page.params.id);
    }
</script>


<UserEdition {user}/>
