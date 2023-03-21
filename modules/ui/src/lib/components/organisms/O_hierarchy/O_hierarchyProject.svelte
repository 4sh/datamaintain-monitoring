<script lang="ts">

    import O_hierarchyEnv from "$lib/components/organisms/O_hierarchy/O_hierarchyEnv.svelte";

    export let project;
</script>

<div class="project">
    <div class="project-name">
        <a href="/projects/{project.id}">{project.name}</a>
    </div>

    <div class="project-envs">
        {#each project.envs as env}
           <O_hierarchyEnv {env} projectRef="{project.id}"/>
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
