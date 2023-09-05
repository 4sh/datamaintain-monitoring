<script lang="ts">

    export let status;
    export let ghostMode = false;
</script>

<div class="mProgressBar grid-x {ghostMode ? '_ghostMode' : ''}">
    <div class="mProgressBar-item cell auto _{status.toLowerCase()}"></div>
    <div class="mProgressBar-item cell auto _{status.toLowerCase()}"></div>
    <div class="mProgressBar-item cell auto _{status.toLowerCase()}"></div>
    <div class="mProgressBar-item cell auto _{status.toLowerCase()}"></div>
</div>

<style lang="scss">
    @import "src/app";

    .mProgressBar {
      border-radius: rem-calc(4px);
      height: rem-calc(8px);
      overflow: hidden;

      &-item {
        margin-right: rem-calc(4px);

        &:last-child {
          margin-right: 0;
        }

        &._in_progress {
          background-color: $app-primary_700;
        }

        &._completed {
          background-color: $app-success_900;
        }

        &._error {
          background-color: $app-error_900;
        }
      }

      &._ghostMode {
        .mProgressBar-item {
          background-color: rgb(var(--secondary-color-rgb) / .1);
        }
      }
    }
</style>
