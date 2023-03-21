<script lang="ts">

	import {onDestroy} from "svelte";
	import type {User} from "$lib/domain/User";
	import type {Searcher} from "$lib/components/molecules/O_pagination/api/Searcher";
	import type {Renderer} from "$lib/components/molecules/O_pagination/api/Renderer";

	export let searcherType: Searcher<User>
	export let rendererType: Renderer<User>

	let nbTotalOfPages = 0
	let currentPage = 0
	let limit = 2

	let searcher
	let renderer

	function previous() {
		currentPage--;
		searcher.api.search(currentPage * limit, limit).then(users => {
			renderer.api.render(users)
		})
	}

	function next() {
		currentPage++;
		searcher.api.search(currentPage * limit, limit).then(users => {
			renderer.api.render(users)
		})
	}

	function onNewSearch() {
		renderer.api.render(searcher.api.getCurrentResults())
		currentPage = 0
		searcher.api.count().then(count => {
			nbTotalOfPages = Math.ceil(count / limit)
		})
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