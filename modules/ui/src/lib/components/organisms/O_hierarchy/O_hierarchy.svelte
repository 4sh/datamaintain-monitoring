<script lang="ts">
    import {ProjectService} from "$lib/services/ProjectService";
    import {HierarchySearch} from "$lib/domain/ProjectHierarchy";
    import O_hierarchyProject from "$lib/components/organisms/O_hierarchy/O_hierarchyProject.svelte";

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
                <O_hierarchyProject {project}/>
            {/each}
        </div>
    </div>
{:catch error}
    <p style="color: red">Env not found !</p>
{/await}

<style>
</style>
