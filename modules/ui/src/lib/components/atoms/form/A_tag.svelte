<script lang="ts">
    export let label: string;
</script>

<span class="aTag grid-x align-middle align-center">{label}</span>

<style lang="scss">
  @import "src/app";

  .aTag {
    background: $app-info_900;
    color: white;
    height: rem-calc(20px);
    padding: 0 rem-calc(6px);
    border-radius: rem-calc(3px);
  }
</style>
