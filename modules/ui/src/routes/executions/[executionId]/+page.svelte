<script lang="ts">
    import A_icon from "$lib/components/atoms/A_icon.svelte";
    import M_breadcrumbItem from "$lib/components/molecules/M_breadcrumbItem.svelte";
    import {Tooltip} from "svelte-tooltip-simple";
    import M_codeBlock from "$lib/components/molecules/M_codeBlock.svelte";
    import O_scriptDetail from "$lib/components/organisms/O_scriptDetail.svelte";
    import M_tabs from "$lib/components/molecules/M_tabs.svelte";
    import O_scriptList from "$lib/components/organisms/O_scriptList.svelte";
    import {page} from "$app/stores";
    import {ExecutionService} from "$lib/services/ExecutionService";
    import type {ExecutionDetail, ScriptExecutionDetail} from '$lib/domain/execution/Execution';

    let executionPromise: Promise<ExecutionDetail>

    type Tab = 'SCRIPT' | 'LOGS';
    let tabItems: Tab[] = ['SCRIPT', 'LOGS']
    let activeTabItem: Tab = 'SCRIPT';

    const triggerTabChange = (event: CustomEvent) => {
        activeTabItem = event.detail;
    }

    let activeScriptExecution: ScriptExecutionDetail

    const onScriptExecutionSelection = (event: CustomEvent) => {
        activeScriptExecution = event.detail;
    }

    $: if($page.params?.executionId) {
        executionPromise = ExecutionService.detailById($page.params.executionId)
            .then(execution => {
                activeScriptExecution = execution.scriptsExecutions[0]
                return execution
            });
    }


</script>

{#await executionPromise}
    <p>...waiting</p>
{:then execution}
    <div class="executionView grid-x">

        <div class="executionView-container cell auto grid-y">
            <div class="executionView-header cell shrink grid-x align-middle">
                <div class="executionView-header-return cell shrink grid-x align-middle">
                    <Tooltip text="Retour">
                        <A_icon type="keyboard_backspace" size="light"></A_icon>
                    </Tooltip>
                </div>
                <div class="executionView-header-breadcrumb cell auto grid-x">
                    <M_breadcrumbItem nameItem="{execution.project.name}"></M_breadcrumbItem>
                    <M_breadcrumbItem nameItem="{execution.environment.name}"></M_breadcrumbItem>
                    <M_breadcrumbItem nameItem="{execution.module.name}"></M_breadcrumbItem>
                    <M_breadcrumbItem nameItem="{execution.id}" isLast="{true}"></M_breadcrumbItem>
                </div>
                <div class="executionView-header-favorite  cell shrink">
                    <Tooltip text="Ajouter aux favoris">
                        <A_icon type="star_outline" size="extraThin"></A_icon>
                    </Tooltip>
                </div>
            </div>

            <div class="executionView-title cell shrink">
                Exécution {execution.id}
            </div>

            <div class="executionView-content cell shrink">
                <div class="executionView-content-container">
                    <div class="executionView-content-title">
                        Lancé le :
                    </div>
                    <div class="executionView-content-data">
                        {execution.startDate.toLocaleDateString()}
                    </div>
                </div>
                <div class="executionView-content-container">
                    <div class="executionView-content-title">
                        Module :
                    </div>
                    <div class="executionView-content-data">
                        {execution.module.name}
                    </div>
                </div>
                <div class="executionView-content-container">
                    <div class="executionView-content-title">
                        Environnement :
                    </div>
                    <div class="executionView-content-data">
                        {execution.environment.name}
                    </div>
                </div>
            </div>

            <div class="executionView-scripts cell auto grid-y">
                <O_scriptList scriptsExecutions="{execution.scriptsExecutions}"
                              activeScriptExecutionId="{activeScriptExecution.id}"
                              on:scriptExecutionSelection={onScriptExecutionSelection}>
                </O_scriptList>
            </div>
        </div>

        <div class="executionView-container cell shrink grid-y">
            <M_tabs tabItems={tabItems} activeItem={activeTabItem} on:tabChange={triggerTabChange}/>

            <div class="executionView-details cell auto grid-y">
                {#if activeTabItem === 'SCRIPT'}
                    <O_scriptDetail scriptExecution="{activeScriptExecution}"></O_scriptDetail>
                {:else if activeTabItem === 'LOGS'}
                    <M_codeBlock codeLog="{activeScriptExecution.output}"></M_codeBlock>
                {/if}
            </div>
        </div>
    </div>
{:catch error}
    <p style="color: red">Project not found !</p>
{/await}

<style lang="scss">
  @import "src/app";

  .executionView {
    height: 100%;

    &-container {
      width: calc(50% - 30px);

      &:first-child {
        padding-right: rem-calc(30px);
      }
      &:last-child {
        padding-left: rem-calc(30px);
      }
    }

    &-header {
      background-color: $app-primary_900;
      height: rem-calc(50px);
      border-radius: rem-calc(8px);
      padding: 0 rem-calc(20px);
      margin-bottom: rem-calc(32px);

      &-favorite {
        height: rem-calc(16px);

        &:hover {
          cursor: pointer;
          color: $app-primary_700;
        }
      }

      &-return {
        height: rem-calc(20px);
        margin-right: rem-calc(10px);

        &:hover {
          cursor: pointer;
          color: $app-primary_700;
        }
      }
    }

    &-title {
      margin-bottom: rem-calc(25px);
      font-weight: 500;
      letter-spacing: rem-calc(1px);
      font-size: rem-calc(24px);
      color: $app-primary_700;
    }

    &-content {
      font-size: rem-calc(14px);

      &-container {
        margin-bottom: rem-calc(10px);
      }

      &-title {
        margin-right: rem-calc(5px);
        font-weight: 600;
      }
    }

    &-scripts {
      height: 100%;
      margin-top: rem-calc(25px);
    }

    &-details {
      height: calc(100% - 50px);
      width: hom(450px, 300);
    }
  }
</style>
