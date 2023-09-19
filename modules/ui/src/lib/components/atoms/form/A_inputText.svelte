<script lang="ts">
    export let value;
    export let placeholder;
    export let required;
</script>

<input type="text" bind:value={value} {required} placeholder="{placeholder}" class="aInputText">

<style lang="scss">
    @import "src/app";

    .aInputText {
      background-color: transparent !important;
      border-top: none;
      border-left: none;
      border-right: none;
      border-radius: 0;
      border-bottom: 0.5px solid $app-primary_700;
      color: $app-secondary_900;
      font-size: rem-calc(13px)!important;
      line-height: rem-calc(16px)!important;
      margin: 0;
      font-weight: 400;
      padding: 0;
      max-height: rem-calc(17px);
      width: 100%;
      outline: none;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      caret-color: $app-primary_700;

      &:focus {
        box-shadow: none;
      }

      &::placeholder {
        font-weight: 400;
        font-style: italic;
        color: rgb(var(--secondary-color-rgb) / .5);
      }

      &[readonly] {
        cursor: pointer!important;
      }
    }
</style>
