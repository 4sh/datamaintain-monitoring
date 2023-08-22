<script lang="ts">
    import O_projectHierarchy from "$lib/components/organisms/O_hierarchy/O_hierarchy.svelte";
    import {currentTheme} from "$lib/stores/theme";
    import M_logoText from "$lib/components/molecules/M_logoText.svelte";
    import logo from "$lib/assets/images/svg/datamaintain-logo.svg";
    import M_buttonSwitch from "$lib/components/molecules/M_buttonSwitch.svelte";
    import O_nav from "$lib/components/organisms/O_nav/O_nav.svelte";

    function toggleTheme() {
        if ($currentTheme === "light") {
            currentTheme.set("dark");
        } else {
            currentTheme.set("light");
        }
    }
</script>

<div class="header grid-y">
    <div class="header-logo cell shrink grid-x align-middle">
        <div class="header-logo-img cell shrink grid-x align-middle align-center">
            <img src={logo} alt="Logo Datamaintain">
        </div>
        <div class="header-logo-name cell auto">
            <M_logoText/>
        </div>
    </div>

    <div class="header-contentNav cell auto">
        <O_nav></O_nav>
        <O_projectHierarchy></O_projectHierarchy>
    </div>

    <div class="header-switch cell shrink">
        <M_buttonSwitch alignment="right" flex="shrink" label="Dark Mode" checked="true"
                        on:click={toggleTheme}></M_buttonSwitch>
    </div>

</div>

<style lang="scss">
  @import "src/app";

  .header {
    height: 100%;

    &-logo {
      padding: rem-calc(30px);

      &-img {
        background-color: rgb(var(--secondary-color-rgb) / .3);
        height: rem-calc(56px);
        width: rem-calc(56px);
        border-radius: rem-calc(8px);
      }

      &-name {
        max-width: calc(100% - 80px);
        margin-left: rem-calc(24px);
      }
    }

    &-contentNav {
      padding: rem-calc(15px 30px);
      overflow: hidden;
    }

    &-switch {
      padding: rem-calc(30px);
      text-align: center;
    }
  }
</style>
