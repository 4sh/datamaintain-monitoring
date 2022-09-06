<script lang="ts">

	import type {Renderer} from "./api/Renderer";
	import type {Searcher} from "./api/Searcher";
	import {onDestroy} from "svelte";
	import type {User} from "../../domain/User";

	export let searcherType: Searcher<User>
	export let rendererType: Renderer<User>

	let nbTotalOfPages = 0
	let currentPage = 0
	let limit = 2

	let searcher
	let renderer

	function previous() {
		currentPage--;
		renderer.api.render(searcher.api.search(currentPage * limit, limit))
	}

	function next() {
		currentPage++;
		renderer.api.render(searcher.api.search(currentPage * limit, limit))
	}

	function onNewSearch() {
		renderer.api.render(searcher.api.getCurrentResults())
		currentPage = 0
		nbTotalOfPages = Math.ceil(searcher.api.count() / limit)
	}

	onDestroy(() => {
		searcher?.$destroy()
		renderer?.$destroy()
	})
</script>

<svelte:component this={searcherType} bind:this={searcher} {limit} on:new-search={onNewSearch}/>
<svelte:component this={rendererType} bind:this={renderer}/>
<div>
	<button on:click={previous} disabled={currentPage <= 0}>Précédent</button>
	<button on:click={next} disabled={currentPage >= nbTotalOfPages - 1}>Suivant</button>
</div>