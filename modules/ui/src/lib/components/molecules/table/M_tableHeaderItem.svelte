<script>
    export let width = 1;
    export let textAlign = 'left';
</script>

<th width="{width}" style="text-align: {textAlign}">
    <slot></slot>
</th>

<style lang="scss">
  @import "src/app";

  th {
    text-align: left;
    padding: rem-calc(10px);
    font-size: rem-calc(13px);
    font-weight: 600;
  }
</style>
