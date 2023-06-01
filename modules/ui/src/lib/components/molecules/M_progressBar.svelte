<script lang="ts">

</script>

<div class="mProgressBar">
    <div class="mProgressBar-item"></div>
    <div class="mProgressBar-item"></div>
    <div class="mProgressBar-item"></div>
    <div class="mProgressBar-item"></div>
</div>

<style lang="scss">
    @import "src/app";

    .mProgressBar {
      display: flex;
      border-radius: rem-calc(4px);
      height: rem-calc(8px);
      overflow: hidden;

      &-item {
        flex: 1 1 0;
        background-color: #63c4db;
        margin-right: rem-calc(4px);

        &:last-child {
          margin-right: 0;
        }
      }
    }
</style>
