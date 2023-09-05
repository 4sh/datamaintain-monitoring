<script context="module" lang="ts">
	import { t } from '$lib/services/I18nService';

	export const prerender = true;
</script>

<svelte:head>
	<title>{$t('app.title')}</title>
	<meta name="description" content="{$t('app.title')}" />
</svelte:head>

<section>
	<h1>
		{$t('app.title')}
	</h1>
</section>

<a href="projects">{$t('project.actions.create')}</a>
