<script lang="ts">
	import Header from '$lib/components/header/Header.svelte';
	import '../app.scss';
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

	<div class="layout">
		<div class="layout-header">
			<Header />
		</div>
		<div class="layout-main">
			<slot />
		</div>
	</div>

{/if}

<style lang="scss">
	@import "src/app";

	.layout {
		display: flex;
		flex-direction: row;
		height: 100%;

		&-header {
			width: 16%;
			min-width: 250px;
			background-color: $primary-color;
		}
		&-main {
			width: 84%;
			max-width: calc(100% - 250px);
		}
	}
</style>
