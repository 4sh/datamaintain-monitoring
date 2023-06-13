<script lang="ts">

import A_icon from "$lib/components/atoms/A_icon.svelte";
import A_indicator from "$lib/components/atoms/A_indicator.svelte";
</script>

<div class="alertItem">
    <div class="alertItem-icon">
        <A_icon type="warning" size="light"></A_icon>
    </div>
    <div class="alertItem-content">
        <div class="alertItem-title">PF12092022-1</div>
        <div class="alertItem-info">Lorem ipsum dolor sit amet, consectetur</div>
    </div>
    <div class="alertItem-shortName">
        <A_indicator label="PoF"></A_indicator>
    </div>
    <div class="alertItem-icon">
        <A_icon type="notifications_none" size="light"></A_icon>
    </div>
</div>

<style lang="scss">
  @import "src/app";

  .alertItem {
    display: flex;
    align-items: center;

    &-content {

    }

    &-title {
      font-size: rem-calc(14px);
    }

    &-info {
      font-size: rem-calc(12px);
      font-weight: 300;
    }
  }

</style>
