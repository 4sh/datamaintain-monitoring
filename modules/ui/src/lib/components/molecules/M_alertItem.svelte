<script lang="ts">

    import A_icon from "$lib/components/atoms/A_icon.svelte";
    import A_indicator from "$lib/components/atoms/A_indicator.svelte";

    export let warning;
    export let ghostMode = false;
</script>

<div class="alertItem grid-x align-middle _{ghostMode ? 'ghostMode' : ''}">
    <div class="alertItem-icon {warning ? '_warning' : ''} cell shrink grid-x align-center align-middle">
        {#if !ghostMode}
            <A_icon type="{warning ? 'warning' : 'error'}" size="light"></A_icon>
        {/if}
    </div>
    <div class="alertItem-content cell auto">
        <div class="alertItem-title">PF12092022-1</div>
        <div class="alertItem-info">Lorem ipsum dolor sit amet, consectetur</div>
    </div>
    <div class="alertItem-shortName cell shrink">
        {#if !ghostMode}
            <A_indicator label="PoF"></A_indicator>
        {/if}
    </div>
    <div class="alertItem-notification cell shrink">
        {#if !ghostMode}
            <A_icon type="notifications_none" size="semiThin"></A_icon>
        {/if}
    </div>
</div>

<style lang="scss">
  @import "src/app";

  .alertItem {
    padding: rem-calc(10px 15px 10px 0);

    &-icon {
      min-width: rem-calc(36px);
      height: rem-calc(36px);
      border-radius: rem-calc(18px);
      background-color: $app-primary_900;
      color: $app-warning_900;

      &._warning {
        color: $app-error_900;
      }
    }

    &-content {
      padding: rem-calc(0 15px 0 20px);
      max-width: calc(100% - 138px);
    }

    &-title {
      font-size: rem-calc(14px);
    }

    &-info {
      font-size: rem-calc(12px);
      line-height: rem-calc(12px);
      font-weight: 300;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }

    &-notification {
      padding-left: rem-calc(15px);
    }

    &._ghostMode {
      .alertItem {
        &-icon {
          background-color: rgb(var(--secondary-color-rgb) / .1);
        }

        &-title {
          color: transparent;
          width: 60%;
          background-color: rgb(var(--secondary-color-rgb) / .1);
          margin-bottom: rem-calc(4px);
          height: rem-calc(13px);
        }

        &-info {
          color: transparent;
          background-color: rgb(var(--secondary-color-rgb) / .1);
        }

        &-shortName {
          color: transparent;
          min-width: rem-calc(30px);
          min-height: rem-calc(20px);
          background-color: rgb(var(--secondary-color-rgb) / .1);
          border-radius: rem-calc(4px);
        }

        &-notification {
          color: transparent;
          width: rem-calc(20px);
          height: rem-calc(20px);
          background-color: rgb(var(--secondary-color-rgb) / .1);
          border-radius: rem-calc(10px);
          padding: 0;
          margin-left: rem-calc(15px);
        }
      }
    }
  }

</style>
