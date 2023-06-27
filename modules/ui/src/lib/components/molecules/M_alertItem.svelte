<script lang="ts">

    import A_icon from "$lib/components/atoms/A_icon.svelte";
    import A_indicator from "$lib/components/atoms/A_indicator.svelte";

    export let warning;
</script>

<div class="alertItem">
    <div class="alertItem-icon {warning ? '_warning' : ''}">
        <A_icon type="{warning ? 'warning' : 'error'}" size="light"></A_icon>
    </div>
    <div class="alertItem-content">
        <div class="alertItem-title">PF12092022-1</div>
        <div class="alertItem-info">Lorem ipsum dolor sit amet, consectetur</div>
    </div>
    <div class="alertItem-shortName">
        <A_indicator label="PoF"></A_indicator>
    </div>
    <div class="alertItem-notification">
        <A_icon type="notifications_none" size="semiThin"></A_icon>
    </div>
</div>

<style lang="scss">
  @import "src/app";

  .alertItem {
    display: flex;
    align-items: center;
    padding: rem-calc(10px 15px 10px 0);

    &-icon {
      min-width: rem-calc(36px);
      height: rem-calc(36px);
      border-radius: rem-calc(18px);
      background-color: $app-primary_900;
      display: flex;
      justify-content: center;
      align-items: center;
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
  }

</style>
