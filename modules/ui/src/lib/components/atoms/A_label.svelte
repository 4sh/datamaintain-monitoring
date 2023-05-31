<script lang="ts">
    export let label;
</script>

<div class="aLabel">
    <div class="aLabel-label">{label}</div>
    <div class="aLabel-content">
        <slot></slot>
    </div>
</div>

<style lang="scss">
  @import "src/app";

  .aLabel {
    display: flex;
    align-items: center;

    &-label {
      font-size: rem-calc(14px);
      font-weight: lighter;
      padding-right: rem-calc(16px);
    }

    &-content {
      flex: 1 1 0;
    }
  }

</style>
