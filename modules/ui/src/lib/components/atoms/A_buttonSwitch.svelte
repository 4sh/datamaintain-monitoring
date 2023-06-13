<script lang="ts">
    export let checked;
</script>

<label class="aButtonSwitch">
    <input type="checkbox" bind:checked={checked} on:click>
    <span class="aButtonSwitch-slider"></span>
</label>

<style lang="scss">
  @import "src/app";

  .aButtonSwitch {
    position: relative;
    display: inline-block;
    width: rem-calc(38px);
    height: rem-calc(16px);
    border: 2px solid $app-secondary_900;
    border-radius: rem-calc(20px);

    input {
      opacity: 0;
      width: 0;
      height: 0;

      &:checked + .aButtonSwitch-slider {
        background-color: rgb(var(--secondary-color-rgb) / .2);

        &:before {
          -webkit-transform: translateX(22px);
          -ms-transform: translateX(22px);
          transform: translateX(22px);
        }
        &:after {
          content: 'ON';
          left: rem-calc(5px);
          -webkit-transition: .4s;
          transition: .4s;
        }
      }
    }

    &-slider {
      position: absolute;
      cursor: pointer;
      top: rem-calc(2px);
      left: rem-calc(2px);
      right: rem-calc(2px);
      bottom: rem-calc(2px);
      background-color: transparent;
      -webkit-transition: .4s;
      transition: .4s;
      border-radius: 34px;

      &:before {
        position: absolute;
        content: "";
        height: rem-calc(12px);
        width: rem-calc(12px);
        left: 0;
        bottom: 0;
        background: linear-gradient(16deg, rgba(99,196,219,1) 0%, rgba(156,222,237,1) 100%);
        -webkit-transition: .4s;
        transition: .4s;
        border-radius: rem-calc(10px);
        box-shadow: rem-calc(-1px 1px 8px) rgb(var(--main-text-color-rgb) / .5);
      }

      &:after {
        content: 'OFF';
        font-size: rem-calc(8px);
        left: rem-calc(15px);
        top: rem-calc(1px);
        position: absolute;
        font-weight: bold;
        -webkit-transition: .4s;
        transition: .4s;
      }
    }
  }
</style>
