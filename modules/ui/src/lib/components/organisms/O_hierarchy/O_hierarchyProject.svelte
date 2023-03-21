<script lang="ts">
    import EnvHierarchy from "./O_hierarchyEnv.svelte";

    export let project;
</script>

<div class="project">
    <div class="project-name">
        <a href="/projects/{project.id}">{project.name}</a>
    </div>

    <div class="project-envs">
        {#each project.envs as env}
           <EnvHierarchy {env} projectRef="{project.id}"/>
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
