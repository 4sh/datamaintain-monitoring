<script lang="ts">
    export let type: string;
    export let size: string;
    export let weight: string;
</script>

<span class="material-icons {size} {weight}">{type}</span>

<style lang="scss">
  @import "src/app";

  .material-icons {

    /* SIZE */

    &.slim {
      font-size: 10px;
    }

    &.semiSlim {
      font-size: 12px;
    }

    &.extraSlim {
      font-size: 13px;
    }

    &.semiThin {
      font-size: 14px;
    }

    &.thin {
      font-size: 15px;
    }

    &.extraThin {
      font-size: 16px;
    }

    &.semiLight {
      font-size: 18px;
    }

    &.light {
      font-size: 20px;
    }

    &.extraLight {
      font-size: 24px;
    }

    &.large {
      font-size: 28px;
    }

    &.semiLarge {
      font-size: 36px;
    }

    &.extraLarge {
      font-size: 50px;
    }

    &.high {
      font-size: 60px;
    }

    /* WEIGHT */

    &.bold {
      font-weight: bold;
    }

    /* VISIBILITY */

    &._secondary {
      color: $app-secondary_900;
    }
  }
</style>
