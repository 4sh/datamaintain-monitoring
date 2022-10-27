<script lang="ts">
    import {ProjectService} from "../../services/ProjectService";
    import ProjectHierarchy from "./ProjectHierarchy.svelte";
    import {HierarchySearch} from "../../domain/ProjectHierarchy";

    let projectHierarchiesPromise = ProjectService.projectHierarchies();

    function filterHierarchy(event) {
        projectHierarchiesPromise = ProjectService.projectHierarchies().then(result => {
            return new HierarchySearch(event.target.value).filter(result);
        })
    }
</script>

{#await projectHierarchiesPromise}
    <p>...waiting</p>
{:then projectHierarchies}
    <div class="hierarchy">
        <input type="text" on:change={filterHierarchy}>

        <div class="projects">
            {#each projectHierarchies as project}
                <ProjectHierarchy {project}/>
            {/each}
        </div>
    </div>
{:catch error}
    <p style="color: red">Env not found !</p>
{/await}

<style>
</style>
