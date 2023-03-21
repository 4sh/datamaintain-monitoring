<svelte:options accessors={true}/>

<script lang="ts">
	import type {Renderer} from "../O_pagination/api/Renderer";
	import type {User} from "../../../domain/User";

	let users = []

	export let api = new class implements Renderer<User> {
		render(items: User[]): void {
			users = items
		}
	}
</script>

<table>
	<thead>
	<tr>
		<th>Nom</th>
		<th>Prénom</th>
		<th>Actions</th>
	</tr>
	</thead>
	<tbody>
	{#each users as user}
		<tr>
			<td>{user.firstName}</td>
			<td>{user.lastName}</td>
			<td>
				<a href="/users/{user.id}/edit">Éditer</a>
			</td>
		</tr>
	{/each}
	</tbody>
</table>
