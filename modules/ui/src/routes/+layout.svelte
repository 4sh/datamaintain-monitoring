<script lang="ts">
	import '../app.scss';
	import {getLocaleFromNavigator, init, isLoading, register} from "svelte-i18n";
	import O_header from "$lib/components/organisms/O_header.svelte";
	import {currentTheme} from "$lib/stores/theme";
	import {page} from "$app/stores";
	import {currentSiteSection, SiteSectionService} from "$lib/services/utils/SiteSectionService";

	register("en", () => import("../public/lang/en.json"));
	register("fr", () => import("../public/lang/fr.json"));

	init({
		fallbackLocale: "en",
		initialLocale: getLocaleFromNavigator()
	});

	page.subscribe(newPage => {
		currentSiteSection.set(SiteSectionService.toSiteSection(newPage.url));
	})
</script>

<svelte:head>
	<!-- The path to the theme is the one after copy task (see vite.config.js) -->
	<link rel="stylesheet" href="/_app/immutable/assets/themes/{$currentTheme}.css" />
</svelte:head>

{#if $isLoading}
	<p>Loading ...</p>
{:else}

	<div class="layout">
		<div class="layout-header">
			<O_header />
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
			background-color: $app-primary_900;
		}
		&-main {
			width: 84%;
			max-width: calc(100% - 250px);
		}
	}
</style>
