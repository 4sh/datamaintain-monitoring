<script>
    import {createEventDispatcher} from 'svelte';

    const dispatch = createEventDispatcher();

    export let tabItems;
    export let activeItem;

</script>

<div class="mTabs cell shrink grid-x align-middle">
    {#each tabItems as item}
        <div on:click={() => dispatch('tabChange', item)}
             class:active={item===activeItem}
             class="mTabs-item cell shrink grid-x align-middle">
            {item}
        </div>
    {/each}
</div>

<style lang="scss">
  @import "src/app";

  .mTabs {

    &-item {
      height: rem-calc(24px);
      margin: rem-calc(10px 13px 16px) 0;
      border-radius: rem-calc(12px);
      padding: 0 rem-calc(14px);
      font-size: rem-calc(14px);
      border: rem-calc(1px) solid rgba($app-secondary_700, .5);

      &:hover {
        cursor: pointer;
        background-color: $app-primary_900;
      }

      &.active {
        background: linear-gradient(16deg, rgba(99, 196, 219, 1) 0%, rgba(156, 222, 237, 1) 100%) !important;
        font-weight: 500;
        border: none;
        color: white;
      }
    }
  }

</style>
