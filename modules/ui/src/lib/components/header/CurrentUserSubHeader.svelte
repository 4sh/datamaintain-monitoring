<script lang="ts">
	import {_, locale} from "svelte-i18n";

	$: myLocal = $locale

	const handleLocaleChange = e => {
		e.preventDefault();
		locale.set(e.target.value);
	};
</script>

<div class="current-user-sub-header">
	<div>User admin</div>

	<div>
		<select bind:value={myLocal} on:change="{handleLocaleChange}">
			<option value="en">en</option>
			<option value="fr">fr</option>
		</select>
	</div>

	<div>
		<input type="button"
			   placeholder="{$_('settings.placeholder')}"
			   class="i-settings i-16">
	</div>

	<div>
		<input type="button"
			   placeholder="{$_('app.actions.logout')}"
			   class="i-logout i-16">
	</div>
</div>
