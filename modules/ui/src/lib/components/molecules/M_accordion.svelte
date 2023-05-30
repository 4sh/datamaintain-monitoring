<script>
    import {Accordion, AccordionItem} from 'svelte-simple-accordion';
    import A_icon from "$lib/components/atoms/A_icon.svelte";

    /* Returns a DOM title item index */
    const getItemIndex = (item, attributeName) => {
        return item.getAttribute(attributeName);
    };

    let collapsedIcon = 'expand_more';

    export let prefixIcon = 'fiber_manual_record';
    export let prefixIconSize = 'semiSlim';

    export let prefixIconWeight = '';

    export let subContentRange = 'primary';

    /* Handles the item clicked event */
    const handleItemClicked = event => {
        /* Gets the event data */
        const accordion = event.detail.accordion;
        const attributeName = event.detail.itemIndexAttributeName;
        const itemTitle = event.detail.itemTitle;
        const opened = event.detail.opened;
        /* Loops on the accordion items */
        accordion.querySelectorAll('[' + attributeName + ']').forEach(element => {
            /* Gets the element data */
            const title = element.querySelector('.item-title'); // The DOM title
            /* Checks if the element is the one clicked */
            const clicked = getItemIndex(itemTitle, attributeName) === getItemIndex(element, attributeName);
            const toOpen = clicked && opened; // The status to appy to the element
            if (toOpen) {
                title.classList.remove('hide'); // The title style will change as opened
                collapsedIcon = 'expand_less';
            } else {
                title.classList.add('hide'); // The title style will change as closed
                collapsedIcon = 'expand_more';
            }
        });
    };
</script>

<Accordion dispatchName="itemClicked" on:itemClicked={handleItemClicked}>
    <div class="item">
        <AccordionItem>
            <!-- TODO DRO : Add dynamicly '_selected' class to item-title -->
            <div slot="title" class="item-title hide _{subContentRange}">
                <div class="item-title-container">
                    <div class="item-title-icon">
                        <A_icon type="{prefixIcon}" size="{prefixIconSize}" weight="{prefixIconWeight}" visibility="secondary"></A_icon>
                    </div>
                    <div class="item-title-label">
                        <slot name="title"></slot>
                    </div>
                </div>
                {#if $$slots.content}
                    <div class="item-title-icon">
                        <A_icon type="{collapsedIcon}" size="light" visibility="secondary"></A_icon>
                    </div>
                {/if}
            </div>
            <div slot="content" class="item-content">
                <slot name="content"></slot>
            </div>
        </AccordionItem>
    </div>
</Accordion>

<style lang="scss">
  @import "src/app";

  .item {

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
        background: linear-gradient(16deg, rgba(99,196,219,1) 0%, rgba(156,222,237,1) 100%);
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
      }

      &._secondary {
        padding-left: rem-calc(38px);

        .item-title-label {
          font-size: rem-calc(13px);
        }
      }

      &._tertiary {
        padding-left: rem-calc(58px);

        .item-title-label {
          font-size: rem-calc(13px);
        }
      }
    }

    &:hover {
      cursor: pointer;
    }
  }

</style>
