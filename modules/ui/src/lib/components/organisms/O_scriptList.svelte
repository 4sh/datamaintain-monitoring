<script lang="ts">
    import {Svroller} from "svrollbar";
    import M_scriptItem from "$lib/components/molecules/M_scriptItem.svelte";
</script>

<div class="scriptList">
    <div class="scriptList-content">
        <Svroller>
            <div class="scriptList-content-scroller">
                <M_scriptItem status="valid"></M_scriptItem>
                <M_scriptItem status="error"></M_scriptItem>
                <M_scriptItem status="valid"></M_scriptItem>
                <M_scriptItem status="valid"></M_scriptItem>
                <M_scriptItem status="progress" active="true"></M_scriptItem>
                <M_scriptItem status="waiting"></M_scriptItem>
                <M_scriptItem status="waiting"></M_scriptItem>
                <M_scriptItem status="waiting"></M_scriptItem>
            </div>
        </Svroller>
    </div>
</div>

<style lang="scss">
  @import "src/app";

  .scriptList {
    background-color: rgb(var(--secondary-color-rgb) / .06);
    border-radius: rem-calc(6px);
    height: 100%;
    display: flex;
    flex-direction: column;

    &-content {
      padding: rem-calc(25px 10px 25px 25px);
      flex: 1 1 0;
      overflow: hidden;

      &-scroller {
        position: relative;

        &:before {
          content: '';
          position: absolute;
          height: calc(100% - 50px);
          width: rem-calc(1px);
          background: $app-neutral_900;
          left: rem-calc(18px);
          z-index: -1;
          margin-top: rem-calc(10px);
        }
      }
    }
  }

</style>
