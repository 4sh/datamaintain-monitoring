<script lang="ts">
	export let user = {}
</script>

<form>
	<input type="text" bind:value={user.firstName}>
	<input type="text" bind:value={user.lastName}>
</form>

<style>
</style>
