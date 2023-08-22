<script lang="ts">
    import A_icon from "$lib/components/atoms/A_icon.svelte";
</script>

<div class="inputSearch grid-x align-middle">
    <div class="inputSearch-input cell auto">
        <input type="text" on:input placeholder="Rechercher">
    </div>
    <div class="inputSearch-icon cell shrink grid-x align-middle align-center">
        <A_icon type="search" size="semiSlim"/>
    </div>
</div>

<style lang="scss">
  @import "src/app";

  .inputSearch {
    background-color: $app-primary_800;
    justify-content: space-between;
    padding: rem-calc(13px);
    border-radius: rem-calc(4px);

    &-input {
      max-width: calc(100% - 20px);

      input {
        border: none;
        background: transparent;
        outline: none;
        font-weight: 400;
        color: $app-secondary_900;

        &::placeholder {
          font-style: italic;
        }
      }
    }
  }
</style>
