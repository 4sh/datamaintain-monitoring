<script lang="ts">

</script>

<div class="mProgressBar grid-x">
    <div class="mProgressBar-item cell auto"></div>
    <div class="mProgressBar-item cell auto"></div>
    <div class="mProgressBar-item cell auto"></div>
    <div class="mProgressBar-item cell auto"></div>
</div>

<style lang="scss">
    @import "src/app";

    .mProgressBar {
      border-radius: rem-calc(4px);
      height: rem-calc(8px);
      overflow: hidden;

      &-item {
        background-color: #63c4db;
        margin-right: rem-calc(4px);

        &:last-child {
          margin-right: 0;
        }
      }
    }
</style>
