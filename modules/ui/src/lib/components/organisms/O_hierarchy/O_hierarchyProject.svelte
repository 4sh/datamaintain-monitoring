<script lang="ts">

    import O_hierarchyEnv from "$lib/components/organisms/O_hierarchy/O_hierarchyEnv.svelte";
    import M_accordion from "$lib/components/molecules/M_accordion.svelte";
    import A_icon from "$lib/components/atoms/A_icon.svelte";

    export let project;
</script>

<M_accordion prefixIcon="folder_open" prefixIconSize="light">
    <a class="projectName" slot="title"
       href="/projects/{project.id}">
        {project.name}
    </a>
    <div slot="content">
        {#each project.envs as env}
            <O_hierarchyEnv {env} projectRef="{project.id}"/>
        {/each}
    </div>
</M_accordion>

<style lang="scss">
  @import "src/app";

  .projectName {
    color: $app-secondary_900;
    text-decoration: none;
  }

</style>
