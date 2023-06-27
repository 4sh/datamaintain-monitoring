<script>
    import A_icon from "$lib/components/atoms/A_icon.svelte";
    import M_user from "$lib/components/molecules/M_user.svelte";
    import M_cardDashboard from "$lib/components/molecules/M_cardDashboard.svelte";
    import {ExecutionService} from "$lib/services/ExecutionService";
    import {Svroller} from "svrollbar";
    import O_alertList from "$lib/components/organisms/O_alertList.svelte";

    let executionsPromise = ExecutionService.searchMostRecent();
</script>

<div class="dashboardView">
    <div class="dashboardView-alert">
        <div class="dashboardView-alert-icon">
            <A_icon type="notifications_none"></A_icon>
        </div>
        <div class="dashboardView-alert-infoContainer">
            <div class="dashboardView-alert-infoTitle">
                Alertes de vos suivis
            </div>
            <div class="dashboardView-alert-infoDetails">
                Attention 3 alertes ont été levées sur vos suivis
            </div>
        </div>
        <div class="dashboardView-alert-user">
            <M_user></M_user>
        </div>
    </div>

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
    padding: rem-calc(30px);
    height: calc(100% - 60px);

    &-alert {
      background-color: $app-primary_900;
      height: rem-calc(62px);
      border-radius: rem-calc(8px);
      display: flex;
      align-items: center;
      padding: 0 rem-calc(20px);
      margin-bottom: rem-calc(32px);

      &-icon {
        padding-right: rem-calc(16px);
      }

      &-infoContainer {
        flex: 1 1 0;
      }

      &-infoTitle {
        color: $app-error_900;
        font-size: rem-calc(15px);
        font-weight: 700;
      }

      &-infoDetails {
        font-size: rem-calc(12px);
      }
    }

    &-title {
      display: flex;
      align-items: center;
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
      display: flex;

      &-cards {
        width: 70%;
        display: flex;
        flex-flow: row wrap;
        margin-right: rem-calc(45px);

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
      }
    }
  }
</style>
