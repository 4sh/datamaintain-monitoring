<script lang="ts">
    import O_projectHierarchy from "$lib/components/organisms/O_hierarchy/O_hierarchy.svelte";
    import {currentTheme} from "$lib/stores/theme";
    import M_logoText from "$lib/components/molecules/M_logoText.svelte";
    import logo from "$lib/assets/images/svg/datamaintain-logo.svg";
    import M_buttonSwitch from "$lib/components/molecules/M_buttonSwitch.svelte";

    function toggleTheme() {
        if ($currentTheme === "light") {
            currentTheme.set("dark");
        } else {
            currentTheme.set("light");
        }
    }
</script>

<div class="header">
    <div class="header-logo">
        <div class="header-logo-img">
            <img src={logo} alt="Logo Datamaintain">
        </div>
        <div class="header-logo-name">
            <M_logoText/>
        </div>
    </div>

    <div class="header-hierarchy">
        <O_projectHierarchy></O_projectHierarchy>
    </div>

    <div class="header-switch">
        <M_buttonSwitch alignment="right" flex="shrink" label="Dark Mode" checked="true"
                        on:click={toggleTheme}></M_buttonSwitch>
    </div>

</div>

<style lang="scss">
  @import "src/app";

  .header {
    height: 100%;
    display: flex;
    flex-direction: column;

    &-logo {
      display: flex;
      flex-direction: row;
      align-items: center;
      padding: 30px;

      &-img {
        background-color: rgb(var(--secondary-color-rgb) / .3);
        height: 56px;
        width: 56px;
        border-radius: 8px;
        display: flex;
        justify-content: center;
        align-items: center;
      }

      &-name {
        max-width: calc(100% - 80px);
        margin-left: 24px;
      }
    }

    &-hierarchy {
      padding: 15px 30px;
      flex: 1 1 0;
    }

    &-switch {
      padding: 30px;
      text-align: center;
    }
  }
</style>
