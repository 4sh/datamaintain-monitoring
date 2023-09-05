<script>
    import A_icon from "$lib/components/atoms/A_icon.svelte";
</script>

<div class="mError grid-x align-middle align-center">
    <div class="cell shrink grid-x align-middle">
        <A_icon type="warning" size="extraThin"></A_icon>
    </div>
    <div class="cell shrink mError-label">
        <slot></slot>
    </div>
</div>

<style lang="scss">
  @import "src/app";

  .mError {
    background-color: $app-error_900;
    height: rem-calc(20px);
    border-radius: rem-calc(4px);

    &-label {
      font-size: rem-calc(12px);
      font-weight: 600;
      margin-left: rem-calc(5px);
    }
  }
</style>
