<script context="module" lang="ts">
	export const prerender = true;
</script>

<script lang="ts">
	import {_} from "svelte-i18n";
</script>

<svelte:head>
	<title>{$_('app.title')}</title>
	<meta name="description" content="Datamaintain monitoring application" />
</svelte:head>

<section>
	<h1>
		{$_('app.title')}
	</h1>
</section>

<a href="projects">Créer un nouveau projet</a>

<style>

</style>
