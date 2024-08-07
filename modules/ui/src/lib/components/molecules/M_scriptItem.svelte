<script lang="ts">

    import A_icon from "$lib/components/atoms/A_icon.svelte";
    import {ExecutionStatus, type ScriptExecutionDetail} from '$lib/domain/execution/Execution';

    export let scriptExecution: ScriptExecutionDetail;
    export let active = false;
    let status = scriptExecution.status
</script>

<div class="scriptItem {active ? '_isActive' : ''} grid-x">
    <div class="scriptItem-icon _{status} cell shrink grid-x align-middle align-center">
        <A_icon type="{status === ExecutionStatus.COMPLETED ? 'task_alt' :
                       status === ExecutionStatus.IN_PROGRESS ? 'autorenew' :
                       status === ExecutionStatus.PLANNED ? 'hourglass_empty' :
                       status === ExecutionStatus.ERROR ? 'error_outline' : ''}" size="light"></A_icon>
    </div>
    <div class="scriptItem-content cell auto">
        <div class="scriptItem-title">{scriptExecution.script.name}</div>
        <div>
            <div class="scriptItem-info">Début : {scriptExecution.startDate.toLocaleDateString()}</div>
            <div class="scriptItem-info">Temps d’exécution : {scriptExecution.durationInMs} ms</div>
        </div>
    </div>
</div>

<style lang="scss">
  @import "src/app";

  .scriptItem {
    padding: rem-calc(0 15px 20px 0);

    &:hover {
      cursor: pointer;

      .scriptItem-title {
        color: $app-primary_700;
        font-weight: 500;
      }
    }

    &._isActive {
      .scriptItem-title {
        color: $app-primary_700;
        font-weight: 500;
      }
    }

    &-icon {
      min-width: rem-calc(36px);
      height: rem-calc(36px);
      border-radius: rem-calc(18px);
      background-color: $app-primary_900;

      &._COMPLETED {
        color: $app-success_900;
      }
      &._IN_PROGRESS {
        color: $app-primary_700;
      }
      &._ERROR {
        color: $app-error_900;
      }
      &._PLANNED {
        color: $app-info_900;
      }
    }

    &-content {
      padding: rem-calc(0 15px 0 20px);
      max-width: calc(100% - 138px);
    }

    &-title {
      font-size: rem-calc(14px);
      margin-bottom: rem-calc(5px);
    }

    &-info {
      font-size: rem-calc(12px);
      line-height: rem-calc(12px);
      font-weight: 300;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;

      &:first-child {
        margin-bottom: rem-calc(5px);
      }
    }
  }

</style>
