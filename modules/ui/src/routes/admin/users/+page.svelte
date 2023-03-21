<script>
    import UserSearcher from "$lib/components/organisms/O_user/O_userSearcher.svelte";
    import UsersRenderer from "$lib/components/organisms/O_user/O_usersRenderer.svelte";
    import O_pagination from "$lib/components/organisms/O_pagination/O_pagination.svelte";


</script>

<O_pagination searcherType="{UserSearcher}" rendererType="{UsersRenderer}"/>
