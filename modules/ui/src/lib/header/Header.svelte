<script lang="ts">
	import {locale} from "svelte-i18n";

	$: myLocal = $locale

	const handleLocaleChange = e => {
		e.preventDefault();
		locale.set(e.target.value);
	};
</script>

<header>
	<div>
		<select bind:value={myLocal} on:change="{handleLocaleChange}">
			<option value="en">en</option>
			<option value="fr">fr</option>
		</select>
	</div>
</header>

<style>

</style>
