<script lang="ts">
    import M_accordion from "$lib/components/molecules/M_accordion.svelte";

    export let projectRef;
    export let env;
</script>

<M_accordion prefixIcon="radio_button_unchecked" prefixIconWeight="bold"
             subContentRange="secondary">
    <a class="hierarchyEnv-title" slot="title"
       href="/projects/{projectRef}/envs/{env.id}">
        {env.name}
    </a>
    <div slot="content">
        {#each env.modules as module}
            <M_accordion subContentRange="tertiary">
                <a class="hierarchyEnv-moduleTitle" slot="title"
                   href="/projects/{projectRef}/modules/{module.id}?env={env.id}">
                    {module.name}
                </a>
            </M_accordion>
        {/each}
    </div>
</M_accordion>

<style lang="scss">
  @import "src/app";

  .hierarchyEnv {
    &-title {
      color: $secondary-color;
      text-decoration: none;
    }
    &-moduleTitle {
      color: $secondary-color;
      text-decoration: none;
    }
  }

</style>
