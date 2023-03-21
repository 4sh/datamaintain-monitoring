<svelte:options accessors={true}/>

<script lang="ts">
	import {UserService} from "../../../services/UserService";
    import {createEventDispatcher} from "svelte";
    import type {User} from "../../../domain/User";
    import type {Searcher} from "../O_pagination/api/Searcher";

    export let limit: number;

    let request = { q: undefined }
	let users = []
    const dispatcher = createEventDispatcher();

    function searchAndDispatch() {
        api.search(0, limit).then(usersFound => {
            users = usersFound
            dispatcher('new-search', {})
        })
    }

    export let api = new class implements Searcher<User> {
        count(): Promise<number> {
            return UserService.count(request)
        }

        getCurrentResults(): User[] {
            return users
        }

        search(skip: number, limit: number): Promise<User[]> {
            return UserService.search(request, skip, limit)
        }
    }
</script>

<input type="text" bind:value={request.q}>
<button type="submit" on:click={searchAndDispatch}>Rechercher</button>
