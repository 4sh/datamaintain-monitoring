<script>
    import UserSearcher from "../../lib/components/user/UserSearcher.svelte";
    import UsersRenderer from "../../lib/components/user/UsersRenderer.svelte";
    import Pagination from "../../lib/components/utils/Pagination.svelte";

</script>

<Pagination searcherType="{UserSearcher}" rendererType="{UsersRenderer}"/>
