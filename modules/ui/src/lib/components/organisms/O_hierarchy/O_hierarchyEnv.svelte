<script lang="ts">
    import M_menuItem from "$lib/components/molecules/M_menuItem.svelte";

    export let projectRef;
    export let env;
</script>

<M_menuItem title="{env.name}"
            prefixIcon="radio_button_unchecked"
            prefixIconWeight="bold"
            url="projects/{projectRef}/envs/{env.id}"
            subContentRange="secondary">
    <div slot="content">
        {#each env.modules as module}
            <M_menuItem title="{module.name}"
                        prefixIcon="fiber_manual_record"
                        url="projects/{projectRef}/modules/{module.id}?env={env.id}"
                        subContentRange="tertiary">
            </M_menuItem>
        {/each}
    </div>
</M_menuItem>

<style lang="scss">
  @import "src/app";

  .hierarchyEnv {
    &-title {
      color: $app-secondary_900;
      text-decoration: none;
    }
    &-moduleTitle {
      color: $app-secondary_900;
      text-decoration: none;
    }
  }

</style>
