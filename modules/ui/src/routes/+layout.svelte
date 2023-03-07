<script lang="ts">
	import Header from '$lib/components/header/Header.svelte';
	import '../app.scss';
	import '../css/icons.css';
	import {getLocaleFromNavigator, init, isLoading, register} from "svelte-i18n";

	register("en", () => import("../public/lang/en.json"));
	register("fr", () => import("../public/lang/fr.json"));

	init({
		fallbackLocale: "en",
		initialLocale: getLocaleFromNavigator()
	});
</script>

{#if $isLoading}
	<p>Loading ...</p>
{:else}
	<Header />

	<main>
		<slot />
	</main>

	<footer>
		<p>footer</p>
	</footer>

{/if}

<style>

</style>
