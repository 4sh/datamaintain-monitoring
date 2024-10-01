<script lang="ts">
    export let href: string;
</script>

<a class="link" href="{href}">
    <slot></slot>
</a>

<style lang="scss">
  @import "src/app";

  .link {
    color: $app-primary_700;

    &:hover {
      cursor: pointer;
      text-decoration: underline;
    }
  }

</style>
