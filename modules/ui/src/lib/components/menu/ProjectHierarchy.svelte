<script lang="ts">
    import EnvHierarchy from "./EnvHierarchy.svelte";

    export let project;
</script>

<div class="project">
    <div class="project-name">{project.name}</div>

    <div class="project-envs">
        {#each project.envs as env}
           <EnvHierarchy {env}/>
        {/each}
    </div>
</div>

<style>
    .project-name {
        font-weight: bold;
    }

    .project-envs {
        padding-left: 10px;
    }
</style>
