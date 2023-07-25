<script lang="ts">
    import A_icon from "$lib/components/atoms/A_icon.svelte";
</script>

<div class="inputSearch">
    <div class="inputSearch-input">
        <input type="text" on:input placeholder="Rechercher">
    </div>
    <div class="inputSearch-icon">
        <A_icon type="search" size="semiSlim"/>
    </div>
</div>

<style lang="scss">
  @import "src/app";

  .inputSearch {
    background-color: $app-primary_800;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    padding: 13px;
    border-radius: 4px;

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

    &-icon {
    }
  }
</style>
