<script lang="ts">
    import A_icon from "$lib/components/atoms/A_icon.svelte";
    import A_indicator from "$lib/components/atoms/A_indicator.svelte";
    import M_progressBar from "$lib/components/molecules/M_progressBar.svelte";
    import type {ExecutionForDashboard} from "$lib/domain/execution/Execution";
    import {ExecutionStatus} from "$lib/domain/execution/Execution";

    export let execution: ExecutionForDashboard;
    export let ghostMode = false;

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

<div class="mCard {toStatusClass(execution.status)} grid-y _{ghostMode ? 'ghostMode' : ''}">
    <div class="mCard-header cell shrink grid-x align-middle">
        <div class="cell auto grid-x align-middle">
            <div class="mCard-icon cell shrink grid-x align-middle align-center">
                {#if !ghostMode}
                    <A_icon type="{execution.status === 'COMPLETED' ? 'task_alt' : 'autorenew'}"
                            size="extraLarge"></A_icon>
                {/if}
            </div>
            <div class="cell auto">
                <div class="mCard-title">
                    <span>{execution.id}</span>
                </div>
                <div class="mCard-module">
                    <span>{execution.module.name}</span>
                </div>
            </div>
        </div>
        <div class="cell shrink grid-x align-middle">
            <div class="mCard-indicators cell shrink grid-x align-middlei">
                <div class="mCard-indicators-item cell shrink">
                    <div><A_indicator label="{execution.project.smallName}"></A_indicator></div>
                </div>

                <div class="mCard-indicators-item cell shrink">
                    <div><A_indicator label="{execution.env.smallName}"></A_indicator></div>
                </div>
            </div>
            <div class="mCard-favorite cell shrink grid-x align-middle">
                <A_icon type="star_border" size="semiLight"></A_icon>
            </div>
        </div>
    </div>
    <div class="mCard-content cell auto">
        <div><span>Début d’execution :</span> <span>{execution.date}</span></div>
        <div><span>Temps d’execution :</span> <span>{execution.duration}</span></div>
        <div><span>Nombre de scripts :</span> <span>{execution.nbScriptsKO + execution.nbScriptsOK}</span></div>
        <div><span>KO :</span> <span>{execution.nbScriptsKO}</span></div>
        <div><span>OK :</span> <span>{execution.nbScriptsOK}</span></div>
    </div>
    <div class="mCard-progressBar cell shrink">
        <span><M_progressBar></M_progressBar></span>
    </div>
    <div class="mCard-link  cell shrink">
        <span>Accéder au détails</span>
    </div>
</div>

<style lang="scss">
  @import "src/app";

  .mCard {
    padding: rem-calc(30px 30px 25px 20px);
    background-color: $app-primary_900;
    border-radius: rem-calc(4px);

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

    &-icon {
      margin-right: rem-calc(12px);
      height: rem-calc(50px);
      color: $app-primary_700;
    }

    &-title {
      font-size: rem-calc(20px);
      line-height: rem-calc(20px);
      margin-bottom: rem-calc(5px);
      font-weight: 600;
      color: $app-primary_700;
    }

    &-module {
      font-size: rem-calc(14px);
    }

    &-indicators {

      &-item {
        margin-left: rem-calc(10px);
      }
    }

    &-favorite {
      margin-left: rem-calc(15px);

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

    &._ghostMode {
      background-color: rgb(var(--secondary-color-rgb) / .1);
      animation: grow 2s ease-in-out infinite;

      @keyframes grow {
        0% {
          transform: scale(0.98);
          opacity: .5;
        }
        50% {
          transform: scale(1);
          opacity: 1;
        }
        100% {
          transform: scale(0.98);
          opacity: .5;
        }
      }

      .mCard {
        &-icon {
          width: rem-calc(50px);
          height: rem-calc(50px);
          background-color: rgb(var(--secondary-color-rgb) / .1);
          border-radius: rem-calc(25px);
        }

        &-title {
          width: calc(100% - 20px);
          background-color: rgb(var(--secondary-color-rgb) / .1);

          span {
            opacity: 0;
          }
        }

        &-module {
          width: calc(100% - 50px);
          background-color: rgb(var(--secondary-color-rgb) / .1);

          span {
            opacity: 0;
          }
        }

        &-indicators-item {
          width: rem-calc(30px);
          height: rem-calc(22px);
          background-color: rgb(var(--secondary-color-rgb) / .1);
          border-radius: rem-calc(4px);

          & > * {
            display: none;
          }
        }

        &-favorite {
          display: none;
        }

        &-content {
          span {
            opacity: 0;
          }
          div {
            background-color: rgb(var(--secondary-color-rgb) / .1);
            height: rem-calc(16px);
            margin-bottom: rem-calc(5px);
            border-radius: rem-calc(4px);
          }
        }

        &-progressBar {
          background-color: rgb(var(--secondary-color-rgb) / .1);
          border-radius: rem-calc(4px);

          span {
            opacity: 0;
          }
        }

        &-link {
          background-color: rgb(var(--secondary-color-rgb) / .1);
          border-radius: rem-calc(4px);
          width: fit-content;
          margin: 0 0 0 auto;
          height: rem-calc(14px);

          span {
            opacity: 0;
          }
        }
      }
    }
  }

</style>
