<script lang="ts">
    export let label;
    export let alignment;
    export let flex;
</script>

<div class="aLabel _{alignment} _{flex}">
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
      justify-content: flex-start;
      display: flex;
    }

    &._right {
      flex-direction: row-reverse;

      .aLabel {

        &-label {
          padding-right: 0;
          padding-left: rem-calc(16px);
        }

        &-content {
          justify-content: flex-end;
        }
      }
    }

    &._shrink {
      width: fit-content;

      .aLabel-content {
        flex: 0 0 auto;
      }
    }
  }

</style>
