<script>
    import A_icon from "$lib/components/atoms/A_icon.svelte";
    import { fade, slide } from 'svelte/transition'
    import { goto } from '$app/navigation';
    import {currentSiteSection, SiteSectionService} from "$lib/services/utils/SiteSectionService";

    export let prefixIcon = 'fiber_manual_record';
    export let prefixIconSize = 'semiSlim';

    export let prefixIconWeight;
    export let title = '';
    export let shortTitle = '';
    export let url;
    export let subContentRange = 'primary';

    let selected = false;

    let isOpen = false;

    function toggleMenu() {
        isOpen = !isOpen;
    }

    function toggleMenuAndGoto() {
        toggleMenu();

        if (url) {
            goto(`/${url}`, { replaceState: true })
        }
    }

    currentSiteSection.subscribe(currentSiteSection => {
        let siteSection = url && SiteSectionService.toSiteSection(`/${url}`);
        selected = siteSection && JSON.stringify(currentSiteSection) === JSON.stringify(siteSection);

        isOpen = selected || siteSection && SiteSectionService.isParentOf(siteSection, currentSiteSection);
    });
</script>

<div class="menuItem grid-y" in:fade>
    <div class="menuItem-title cell shrink grid-x align-middle _{subContentRange}" class:_selected={selected}>
        <div class="menuItem-title-container cell auto grid-x align-middle" on:click={toggleMenuAndGoto}>
            <div class="menuItem-title-icon cell shrink">
                <A_icon type="{prefixIcon}" size="{prefixIconSize}" weight="{prefixIconWeight}" visibility="secondary"></A_icon>
            </div>
            <div class="menuItem-title-label cell auto">
                {title} <span>{shortTitle}</span>
            </div>
        </div>
        {#if subContentRange !== 'tertiary'}
            <div class="menuItem-title-icon cell shrink grid-x align-center align-middle {isOpen ? '_isOpen' : ''}" on:click={toggleMenu}>
                <A_icon type="expand_more" size="light" visibility="secondary"></A_icon>
            </div>
        {/if}
    </div>
    {#if isOpen}
        <div class="menuItem-content cell shrink" transition:slide|local>
            <slot name="content"></slot>
        </div>
    {/if}
</div>

<style lang="scss">
  @import "src/app";

  .menuItem {

    &-title {
      padding: 0 rem-calc(8px);
      height: rem-calc(36px);
      border-radius: rem-calc(4px);

      &:hover {
        background: rgb(var(--secondary-color-rgb) / .2);
      }

      &._selected {
        background: linear-gradient(16deg, rgba(99, 196, 219, 1) 0%, rgba(156, 222, 237, 1) 100%);
      }

      &-label {
        font-size: rem-calc(15px);
        line-height: rem-calc(20px);
        margin-left: rem-calc(10px);
        font-weight: 400;

        span {
          color: $app-primary_700;
          font-size: rem-calc(13px);
          margin-left: rem-calc(3px);
          text-transform: uppercase;
        }
      }

      &-icon {
        height: rem-calc(20px);
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
