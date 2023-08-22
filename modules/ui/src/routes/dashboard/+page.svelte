<script>
    import M_cardDashboard from "$lib/components/molecules/M_cardDashboard.svelte";
    import {ExecutionService} from "$lib/services/ExecutionService";
    import {Svroller} from "svrollbar";
    import O_alertList from "$lib/components/organisms/O_alertList.svelte";

    let executionsPromise = ExecutionService.searchMostRecent();
    let ghostMode = true;

    function toggleGhostMode() {
        ghostMode = !ghostMode;
    }
</script>

<div class="dashboardView grid-y">

    <div class="dashboardView-title cell shrink grid-x align-middle">
        <div class="dashboardView-title-name cell shrink">
            Dashboard
        </div>
        <div class="dashboardView-title-details cell shrink">
            Exécutions en cours
        </div>
    </div>

    <div class="dashboardView-content cell auto grid-x">
        <div class="cell auto">
            <button on:click={toggleGhostMode}>ghostmode</button>
            <Svroller>
                <div class="dashboardView-content-cards grid-x">

                    {#await executionsPromise}
                        <p>...waiting</p>
                    {:then executions}
                        {#each executions as execution, i}
                            <div class="dashboardView-content-cards-item">
                                <M_cardDashboard {execution} ghostMode="{ghostMode}"></M_cardDashboard>
                            </div>
                        {/each}
                    {:catch error}
                        <p style="color: red">Error to display dashboard</p>
                    {/await}
                </div>
            </Svroller>
        </div>

        <div class="dashboardView-content-alerts cell shrink">
           <O_alertList></O_alertList>
        </div>
    </div>
</div>

<style lang="scss">
  @import "src/app";

  .dashboardView {
    height: 100%;

    &-title {
      margin-bottom: rem-calc(32px);

      &-name {
        font-size: rem-calc(24px);
        padding-right: rem-calc(14px);
        border-right: rem-calc(1px) solid $app-neutral_900;
        margin-right: rem-calc(14px);
      }

      &-details {
        font-size: rem-calc(14px);
      }
    }

    &-content {
      overflow: hidden;

      &-cards {
        margin-right: rem-calc(25px);

        &-item {
          width: calc(50% - 15px);
          margin-bottom: rem-calc(30px);

          &:nth-child(odd) {
            padding-right: rem-calc(15px);
          }

          &:nth-child(even) {
            padding-left: rem-calc(15px);
          }
        }
      }

      &-alerts {
        width: 30%;
        margin-left: rem-calc(20px);
      }
    }
  }
</style>
