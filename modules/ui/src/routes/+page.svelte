<script context="module" lang="ts">
	export const prerender = true;
</script>

<script lang="ts">

import Hierarchy from "../lib/components/Hierarchy.svelte";
</script>

<svelte:head>
	<title>Home</title>
	<meta name="description" content="Svelte demo app" />
</svelte:head>

<section>
	<h1>
		Home
	</h1>
</section>

<Hierarchy></Hierarchy>

<style>

</style>
