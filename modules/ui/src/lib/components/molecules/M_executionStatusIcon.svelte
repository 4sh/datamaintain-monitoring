<script lang="ts">
    import {ExecutionStatus} from '$lib/domain/execution/Execution';
    import A_icon from '$lib/components/atoms/A_icon.svelte';

    export let executionStatus: ExecutionStatus;
    export let size = "light";
</script>

<div class="icon _{executionStatus}">
    <A_icon type="{executionStatus === ExecutionStatus.COMPLETED ? 'task_alt' :
                           executionStatus === ExecutionStatus.IN_PROGRESS ? 'autorenew' :
                           executionStatus === ExecutionStatus.PLANNED ? 'hourglass_empty' :
                           executionStatus === ExecutionStatus.ERROR ? 'error_outline' : ''}" size="{size}">
    </A_icon>
</div>

<style lang="scss">
    @import "src/app";

    .icon {
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
</style>