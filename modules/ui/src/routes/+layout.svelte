<script lang="ts">
    import '../app.scss';
    import O_header from "$lib/components/organisms/O_header.svelte";
    import {currentTheme} from "$lib/stores/theme";
    import {page} from "$app/stores";
    import {currentSiteSection, SiteSectionService} from "$lib/services/utils/SiteSectionService";
    import A_icon from "$lib/components/atoms/A_icon.svelte";
    import M_user from "$lib/components/molecules/M_user.svelte";

    page.subscribe(newPage => {
        currentSiteSection.set(SiteSectionService.toSiteSection(newPage.url));
    })
</script>

<svelte:head>
    <!-- The path to the theme is the one after copy task (see vite.config.js) -->
    <link rel="stylesheet" href="/_app/immutable/assets/themes/{$currentTheme}.css"/>
</svelte:head>

<div class="layout grid-x">
    <div class="layout-header cell shrink">
        <O_header/>
    </div>
    <div class="layout-main cell auto grid-y">
        <div class="layout-topBar cell shrink grid-x align-middle">
            <div class="layout-topBar-icon cell shrink">
                <A_icon type="notifications_none"></A_icon>
            </div>
            <div class="layout-topBar-infoContainer cell auto">
                <div class="layout-topBar-infoTitle">
                    Alertes de vos suivis
                </div>
                <div class="layout-topBar-infoDetails">
                    Attention 3 alertes ont été levées sur vos suivis
                </div>
            </div>
            <div class="layout-topBar-user cell shrink">
                <M_user src="/src/lib/assets/images/jpg/john_doe.jpeg"
                        name="John Doe">
                </M_user>
            </div>
        </div>
        <div class="layout-content cell auto">
            <slot/>
        </div>

    </div>
</div>

<style lang="scss">
  @import "src/app";

  .layout {
    height: 100%;

    &-header {
      width: hom(250px, 50);
      background-color: $app-primary_900;
    }

    &-main {
      padding: rem-calc(30px);
      height: calc(100% - 60px);
    }

    &-content {
      height: calc(100% - 95px);
    }

    &-topBar {
      background-color: $app-primary_900;
      height: rem-calc(62px);
      border-radius: rem-calc(8px);
      padding: 0 rem-calc(20px);
      margin-bottom: rem-calc(32px);

      &-icon {
        padding-right: rem-calc(16px);
      }

      &-infoTitle {
        color: $app-error_900;
        font-size: rem-calc(15px);
        font-weight: 700;
      }

      &-infoDetails {
        font-size: rem-calc(12px);
      }
    }
  }
</style>
