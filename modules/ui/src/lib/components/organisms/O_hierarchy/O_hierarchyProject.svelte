<script lang="ts">

    import O_hierarchyEnv from "$lib/components/organisms/O_hierarchy/O_hierarchyEnv.svelte";
    import M_menuItem from "$lib/components/molecules/M_menuItem.svelte";

    export let project;
</script>

<M_menuItem prefixIcon="folder_open"
        prefixIconSize="light"
        title="{project.name}"
        url="projects/{project.id}">
    <div slot="content">
        {#each project.envs as env}
            <O_hierarchyEnv {env} projectRef="{project.id}"/>
        {/each}
    </div>
</M_menuItem>

<style lang="scss">
  @import "src/app";

  .projectName {
    color: $app-secondary_900;
    text-decoration: none;
  }

</style>
