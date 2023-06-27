<script lang="ts">
    import A_icon from "$lib/components/atoms/A_icon.svelte";
    export let nameItem;
    export let isLast;
</script>

<div class="breadcrumbItem">
    <a class="breadcrumbItem-name">
        {nameItem}
    </a>
    {#if !isLast}
        <div class="breadcrumbItem-arrow">
            <A_icon type="chevron_right" size="light"></A_icon>
        </div>
    {/if}
</div>

<style lang="scss">
  @import "src/app";

  .breadcrumbItem {
    display: flex;
    align-items: center;

    &-name {
      font-size: rem-calc(14px);
      font-weight: 300;

      &:hover {
        color: $app-primary_700;
        cursor: pointer;
      }
    }

    &-arrow {
      display: flex;
      align-items: center;
      color: $app-neutral_900;
    }
  }
</style>
