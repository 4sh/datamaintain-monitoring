<script lang="ts">
    import A_icon from "$lib/components/atoms/A_icon.svelte";
    import A_indicator from "$lib/components/atoms/A_indicator.svelte";
    import M_progressBar from "$lib/components/molecules/M_progressBar.svelte";
    import type {ExecutionForDashboard} from "$lib/domain/execution/Execution";
    import {ExecutionStatus} from "$lib/domain/execution/Execution";

    export let execution: ExecutionForDashboard;

    function toStatusClass(status: ExecutionStatus) {
        var statusClass;

        switch (status) {
            case ExecutionStatus.PLANNED:
                statusClass = '';
                break;
            case ExecutionStatus.IN_PROGRESS:
                statusClass = '_inProgress';
                break;
            case ExecutionStatus.COMPLETED:
                statusClass = '_check';
                break;
            case ExecutionStatus.ERROR:
                statusClass = '_error';
                break;
        }

        return statusClass;
    }
</script>

<div class="mCard {toStatusClass(execution.status)}">
    <div class="mCard-header">
        <div class="flexAuto">
            <div class="mCard-icon">
                <A_icon type="{execution.status === 'COMPLETED' ? 'task_alt' : 'autorenew'}" size="extraLarge"></A_icon>
            </div>
            <div>
                <div class="mCard-title">
                    {execution.id}
                </div>
                <div class="mCard-module">
                    {execution.module.name}
                </div>
            </div>
        </div>
        <div class="flexShrink">
            <div class="mCard-indicators">
                <div class="mCard-indicators-item">
                    <A_indicator label="{execution.project.smallName}"></A_indicator>
                </div>

                <div class="mCard-indicators-item">
                    <A_indicator label="{execution.env.smallName}"></A_indicator>
                </div>
            </div>
            <div class="mCard-favorite">
                <A_icon type="star_border" size="semiLight"></A_icon>
            </div>
        </div>
    </div>
    <div class="mCard-content">
            <span>Début d’execution :</span> {execution.date}
            <br>

            <span>Temps d’execution :</span> {execution.duration}
            <br>

            <span>Nombre de scripts :</span> {execution.nbScriptsKO + execution.nbScriptsOK}
            <br>

            <span>KO :</span> {execution.nbScriptsKO}
            <slot name="nbKo"></slot>
            <br>

            <span>OK :</span> {execution.nbScriptsOK}
            <slot name="nbOk"></slot>
    </div>
    <div class="mCard-progressBar">
        <M_progressBar></M_progressBar>
    </div>
    <div class="mCard-link">
        Accéder au détails
    </div>
</div>

<style lang="scss">
  @import "src/app";

  .mCard {
    padding: rem-calc(30px 30px 25px 20px);
    background-color: $app-primary_900;
    border-radius: rem-calc(4px);
    flex: 1 1 0;
    display: flex;
    flex-direction: column;

    &._check {
      .mCard-icon, .mCard-title {
        color: $app-success_900;
      }
    }

    &._error {
      .mCard-icon, .mCard-title {
        color: $app-error_900;
      }
    }

    &-header {
      display: flex;

      .flexAuto {
        display: flex;
        align-items: center;
        flex: 1 1 0;
      }

      .flexShrink {
        display: flex;
        align-items: center;
        height: rem-calc(30px);
      }
    }

    &-icon {
      margin-right: rem-calc(12px);
      height: rem-calc(50px);
      color: $app-primary_700;
    }

    &-title {
      font-size: rem-calc(20px);
      font-weight: 600;
      color: $app-primary_700;
    }

    &-module {
      font-size: rem-calc(14px);
    }

    &-indicators {
      display: flex;

      &-item {
        margin-left: rem-calc(10px);
      }
    }

    &-favorite {
      margin-left: rem-calc(15px);
      display: flex;
      align-items: center;

      &:hover {
        cursor: pointer;
      }
    }

    &-content {
      padding: rem-calc(20px 6px 10px);
      font-size: rem-calc(14px);
      font-weight: 300;

      span {
        font-weight: 500;
      }
    }

    &-progressBar {
      margin: rem-calc(10px) 0 rem-calc(15px);
    }

    &-link {
      font-size: rem-calc(14px);
      text-align: right;

      &:hover {
        cursor: pointer;
      }
    }
  }

</style>
