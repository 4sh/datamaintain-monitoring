<script lang="ts">

import {Svroller} from "svrollbar";
import M_alertItem from "$lib/components/molecules/M_alertItem.svelte";
</script>

<div class="alertList">
    <div class="alertList-header">
        <div class="alertList-header-title">
            Toutes les alertes
        </div>
        <div class="alertList-header-info">
            Il y a 7 alertes sur vos projets qui n’ont pas été acquittées
        </div>
    </div>
    <div class="alertList-content">
        <Svroller>
           <M_alertItem></M_alertItem>
           <M_alertItem></M_alertItem>
           <M_alertItem></M_alertItem>
           <M_alertItem></M_alertItem>
           <M_alertItem></M_alertItem>
           <M_alertItem></M_alertItem>
           <M_alertItem></M_alertItem>
           <M_alertItem></M_alertItem>
           <M_alertItem></M_alertItem>
           <M_alertItem></M_alertItem>
           <M_alertItem></M_alertItem>
           <M_alertItem></M_alertItem>
           <M_alertItem></M_alertItem>
        </Svroller>
    </div>
</div>

<style lang="scss">
  @import "src/app";

  .alertList {
    background-color: rgb(var(--secondary-color-rgb) / .06);
    border-radius: rem-calc(6px);
    height: 100%;
    display: flex;
    flex-direction: column;

    &-header {
      background-color: $app-primary_900;
      border-radius: rem-calc(6px);
      padding: rem-calc(20px 25px);
      flex: 0 0 auto;

      &-title {
        font-size: rem-calc(16px);
      }
      &-info {
        font-size: rem-calc(11px);
        font-weight: 300;
        margin-top: rem-calc(5px);
      }
    }

    &-content {
      padding: rem-calc(25px 10px 25px 25px);
      flex: 1 1 0;
      overflow: hidden;
    }
  }

</style>
