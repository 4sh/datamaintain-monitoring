<script lang="ts">

import A_icon from "$lib/components/atoms/A_icon.svelte";
</script>

    <div class="nav">
        <div class="nav-title">
            Navigation
        </div>
        <div class="nav-content">
            <a class="nav-item" href="/dashboard">
                <A_icon type="grid_view" size="light" class="nav-item-icon"></A_icon>
                <div class="nav-item-label">Dashboard</div>
            </a>
            <a class="nav-item" href="/history">
                <A_icon type="schedule" size="light" class="nav-item-icon"></A_icon>
                <div class="nav-item-label">Historique</div>
            </a>
        </div>
    </div>

<style lang="scss">
  @import "src/app";

  .nav {
    margin-bottom: rem-calc(60px);

    &-title {
      font-size: rem-calc(14px);
      text-transform: uppercase;
      margin-bottom: rem-calc(15px);
    }

    &-content {
      margin: {
        right: rem-calc(-14px);
        left: rem-calc(-14px);
      };
    }

    &-item {
      display: flex;
      text-decoration: none;
      align-items: center;
      color: $app-secondary_900;
      height: rem-calc(36px);
      border-radius: rem-calc(4px);
      padding: 0 rem-calc(12px);

      &-label {
        font-size: rem-calc(15px);
        font-weight: 300;
        margin-left: rem-calc(10px);
      }

      &:hover {
        background: rgb(var(--secondary-color-rgb) / .2);
      }
    }
  }

</style>
