<script>
    export let width = 1;
    export let textAlign = 'left';
</script>

<td width="{width}" style="text-align: {textAlign}">
    <slot></slot>
</td>

<style lang="scss">
  @import "src/app";

  td {
    padding: rem-calc(5px 10px);
    font-size: rem-calc(14px);
    font-weight: 400;
  }
</style>
