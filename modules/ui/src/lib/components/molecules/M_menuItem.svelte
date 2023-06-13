<script>
    import A_icon from "$lib/components/atoms/A_icon.svelte";
    import { fade, slide } from 'svelte/transition'

    export let prefixIcon = 'fiber_manual_record';
    export let prefixIconSize = 'semiSlim';

    export let prefixIconWeight = '';

    export let subContentRange = 'primary';
    export let title = '';
    let isOpen = false;
</script>

<div class="menuItem" transition:fade>
    <div class="menuItem-title _{subContentRange}" on:click={() => (isOpen = !isOpen)}>
        <div class="menuItem-title-container">
            <div class="menuItem-title-icon">
                <A_icon type="{prefixIcon}" size="{prefixIconSize}" weight="{prefixIconWeight}" visibility="secondary"></A_icon>
            </div>
            <div class="menuItem-title-label">
                {title}
            </div>
        </div>
        {#if subContentRange !== 'tertiary'}
            <div class="menuItem-title-icon {isOpen ? '_isOpen' : ''}">
                <A_icon type="expand_more" size="light" visibility="secondary"></A_icon>
            </div>
        {/if}
    </div>
    {#if isOpen}
        <div class="menuItem-content" transition:slide|local>
            <slot name="content"></slot>
        </div>
    {/if}
</div>

<style lang="scss">
  @import "src/app";

  .menuItem {

    &-title {
      display: flex;
      flex-direction: row;
      align-items: center;
      padding: 0 rem-calc(8px);
      height: rem-calc(36px);
      border-radius: rem-calc(4px);

      &:hover {
        background: rgb(var(--secondary-color-rgb) / .2);
      }

      &._selected {
        background: linear-gradient(16deg, rgba(99, 196, 219, 1) 0%, rgba(156, 222, 237, 1) 100%);
      }

      &-container {
        display: flex;
        flex-direction: row;
        flex: 1 1 0;
      }

      &-label {
        font-size: rem-calc(15px);
        line-height: rem-calc(20px);
        margin-left: rem-calc(10px);
        font-weight: 400;
      }

      &-icon {
        height: rem-calc(20px);
        display: flex;
        align-items: center;
        transition: transform 300ms ease;

        &._isOpen {
          transform: rotate(180deg);
          transition: transform 300ms ease;
        }
      }

      &._secondary {
        padding-left: rem-calc(38px);

        .menuItem-title-label {
          font-size: rem-calc(13px);
        }
      }

      &._tertiary {
        padding-left: rem-calc(58px);

        .menuItem-title-label {
          font-size: rem-calc(13px);
        }
      }
    }

    &:hover {
      cursor: pointer;
    }
  }

</style>
