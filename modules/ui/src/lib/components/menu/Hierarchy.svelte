<script lang="ts">
    import {ProjectService} from "../../services/ProjectService";
    import ProjectHierarchy from "./ProjectHierarchy.svelte";
    import {HierarchySearch} from "../../domain/ProjectHierarchy";

    let projectHierarchies = ProjectService.projectHierarchies();

    function filterHierarchy(event) {
        projectHierarchies = new HierarchySearch(event.target.value)
            .filter(ProjectService.projectHierarchies());
    }
</script>

<div class="hierarchy">
    <input type="text" on:change={filterHierarchy}>

    <div class="projects">
        {#each projectHierarchies as project}
            <ProjectHierarchy {project}/>
        {/each}
    </div>
</div>

<style>
</style>
