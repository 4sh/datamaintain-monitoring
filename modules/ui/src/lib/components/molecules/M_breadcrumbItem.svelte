<script lang="ts">
    import A_icon from "$lib/components/atoms/A_icon.svelte";
    export let nameItem: string;
    export let isLast = false;
</script>

<div class="breadcrumbItem grid-x cell shrink align-middle">
    <a class="breadcrumbItem-name cell shrink">
        {nameItem}
    </a>
    {#if !isLast}
        <div class="breadcrumbItem-arrow cell shrink">
            <A_icon type="chevron_right" size="light"></A_icon>
        </div>
    {/if}
</div>

<style lang="scss">
  @import "src/app";

  .breadcrumbItem {
    height: rem-calc(20px);

    &-name {
      font-size: rem-calc(13px);
      line-height: rem-calc(20px);
      font-weight: 300;

      &:hover {
        color: $app-primary_700;
        cursor: pointer;
      }
    }

    &-arrow {
      color: $app-neutral_900;
    }
  }
</style>
