<script>
    import M_cardDashboard from "$lib/components/molecules/M_cardDashboard.svelte";
    import {ExecutionService} from "$lib/services/ExecutionService";
    import {Svroller} from "svrollbar";
    import O_alertList from "$lib/components/organisms/O_alertList.svelte";

    let executionsPromise = ExecutionService.searchMostRecent();
</script>

<div class="dashboardView">

    <div class="dashboardView-title">
        <div class="dashboardView-title-name">
            Dashboard
        </div>
        <div class="dashboardView-title-details">
            Exécutions en cours
        </div>
    </div>

    <div class="dashboardView-content">
        <Svroller>
            <div class="dashboardView-content-cards">

                {#await executionsPromise}
                    <p>...waiting</p>
                {:then executions}
                    {#each executions as execution, i}
                    <div class="dashboardView-content-cards-item">
                        <M_cardDashboard {execution}></M_cardDashboard>
                    </div>
                    {/each}
                {:catch error}
                    <p style="color: red">Error to display dashboard</p>
                {/await}
            </div>
        </Svroller>

        <div class="dashboardView-content-alerts">
           <O_alertList></O_alertList>
        </div>
    </div>
</div>

<style lang="scss">
  @import "src/app";

  .dashboardView {
    height: 100%;
    display: flex;
    flex-direction: column;

    &-title {
      display: flex;
      align-items: center;
      margin-bottom: rem-calc(32px);
      flex: 0 0 auto;

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
      display: flex;
      overflow: hidden;
      flex: 1 1 0;

      &-cards {
        display: flex;
        flex-flow: row wrap;
        margin-right: rem-calc(25px);
        flex: 1 1 0;

        &-item {
          width: calc(50% - 15px);
          margin-bottom: rem-calc(30px);
          display: flex;

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
