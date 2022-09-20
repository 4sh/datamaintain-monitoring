<script lang="ts">
    import {ProjectService} from "../services/ProjectService";
    import ProjectHierarchy from "./ProjectHierarchy.svelte";

    let projectHierarchies = ProjectService.projectHierarchies();

    function filterHierarchy(event) {
        let search = event.target.value;

        projectHierarchies = ProjectService.projectHierarchies();

        if (search !== "") {
            projectHierarchies = projectHierarchies
                .map(project => {
                    project.envs = project.envs
                        .map(env => {
                            env.modules = env.modules.filter(module => {
                                return module.name.startsWith(search)
                            });

                            return env;
                        })
                        .filter(env => {
                            return env.modules.length !== 0 || env.name.startsWith(search)
                        });

                    return project;
                })
                .filter(project => {
                    return project.envs.length !== 0 || project.name.startsWith(search)
                });
        }
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
