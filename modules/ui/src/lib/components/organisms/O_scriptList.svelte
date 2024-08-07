<script lang="ts">
    import {Svroller} from "svrollbar";
    import M_scriptItem from "$lib/components/molecules/M_scriptItem.svelte";
    import type {ScriptExecutionDetail} from '$lib/domain/execution/Execution';
    import {createEventDispatcher} from 'svelte';

    const dispatch = createEventDispatcher();
    export let scriptsExecutions: ScriptExecutionDetail[]
    export let activeScriptExecutionId: string;

    function onScriptItemClick(scriptExecution: ScriptExecutionDetail) {
        dispatch('scriptExecutionSelection', scriptExecution);
    }
</script>

<div class="scriptList cell auto grid-y">
    <div class="scriptList-content cell auto">
        <Svroller>
            <div class="scriptList-content-scroller">
                {#each scriptsExecutions as scriptExecution}
                    <M_scriptItem scriptExecution="{scriptExecution}"
                                  active="{activeScriptExecutionId === scriptExecution.id}"
                                  on:click={() => onScriptItemClick(scriptExecution)}>
                    </M_scriptItem>
                {/each}
            </div>
        </Svroller>
    </div>
</div>

<style lang="scss">
  @import "src/app";

  .scriptList {
    background-color: rgb(var(--secondary-color-rgb) / .06);
    border-radius: rem-calc(6px);
    height: 100%;

    &-content {
      padding: rem-calc(25px 10px 25px 25px);
      flex: 1 1 0;
      overflow: hidden;

      &-scroller {
        position: relative;

        &:before {
          content: '';
          position: absolute;
          height: calc(100% - 50px);
          width: rem-calc(1px);
          background: $app-neutral_900;
          left: rem-calc(18px);
          z-index: -1;
          margin-top: rem-calc(10px);
        }
      }
    }
  }

</style>
