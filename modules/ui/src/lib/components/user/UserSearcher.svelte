<svelte:options accessors={true}/>

<script lang="ts">
	import {UserService} from "../../services/UserService";
    import {createEventDispatcher} from "svelte";
    import type {User} from "../../domain/User";
    import type {Searcher} from "../../services/Searcher";

    export let limit: number;

    let request = { q: undefined }
	let users = []
    const dispatcher = createEventDispatcher();

    function searchAndDispatch() {
        users = api.search(0, limit)

        dispatcher('new-search', {})
    }

    export let api = new class implements Searcher<User> {
        count(): number {
            return UserService.count(request)
        }

        getCurrentResults(): User[] {
            return users
        }

        search(skip: number, limit: number): User[] {
            users = UserService.search(request, skip, limit)

            return users
        }
    }
</script>

<input type="text" bind:value={request.q}>
<button type="submit" on:click={searchAndDispatch}>Rechercher</button>
