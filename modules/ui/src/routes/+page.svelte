<script context="module" lang="ts">
	export const prerender = true;
</script>

<script lang="ts">

import {_} from "svelte-i18n";
import { currentTheme } from '$lib/stores/theme';

</script>

<svelte:head>
	<title>{$_('app.title')}</title>
	<meta name="description" content="Datamaintain monitoring application" />

	<link rel="stylesheet" href="./{$currentTheme}.css" />
</svelte:head>

<section>
	<h1>
		{$_('app.title')}
	</h1>
</section>

<a href="projects">Créer un nouveau projet</a>

<style>

</style>
