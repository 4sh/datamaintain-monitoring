<script>
    export let label;
</script>

<div class="aIndicator grid-x align-middle">
    {label}
</div>

<style lang="scss">
    @import "src/app";

    .aIndicator {
      background-color: rgba($app-primary_700, .5);
      font-size: rem-calc(13px);
      height: rem-calc(22px);
      padding: 0 rem-calc(6px);
      border-radius: rem-calc(4px);
    }
</style>
