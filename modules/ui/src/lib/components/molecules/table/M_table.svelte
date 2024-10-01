<table>
    <thead>
    <tr>
        <slot name="headerContent"></slot>
    </tr>
    </thead>
    <tbody>
    <slot name="bodyContent"></slot>
    </tbody>
</table>

<style lang="scss">
  @import "src/app";

  table {
    background-color: rgb(var(--secondary-color-rgb) / .05);
    border-radius: rem-calc(6px);
    border-spacing: inherit;
    overflow: hidden;

    thead {
      background-color: $app-primary_900;
    }
  }
</style>
