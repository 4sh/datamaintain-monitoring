<script lang="ts">
	import {UserService} from "../../services/UserService";

	export let userId
	let user

	$: if (userId) {
		user = UserService.byId(userId)
	}
</script>

<form>
	<input type="text" bind:value={user.firstName}>
	<input type="text" bind:value={user.lastName}>
</form>

<style>
</style>
