<script lang="ts">
    import A_icon from "$lib/components/atoms/A_icon.svelte";
    import { t } from '$lib/services/I18nService';
</script>

    <div class="nav grid-y align-middle">
        <div class="nav-title cell shrink">
            Navigation
        </div>
        <div class="nav-content cell shrink">
            <a class="nav-item grid-x align-middle" href="/dashboard">
                <A_icon type="grid_view" size="light" class="nav-item-icon cell shrink"></A_icon>
                <div class="nav-item-label cell shrink">{$t('app.dashboard')}</div>
            </a>
            <a class="nav-item grid-x align-middle" href="/history">
                <A_icon type="schedule" size="light" class="nav-item-icon cell shrink"></A_icon>
                <div class="nav-item-label cell shrink">{$t('app.history')}</div>
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
      color: $app-secondary_900;
      height: rem-calc(36px);
      border-radius: rem-calc(4px);
      padding: 0 rem-calc(12px);
      text-decoration: none;

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
