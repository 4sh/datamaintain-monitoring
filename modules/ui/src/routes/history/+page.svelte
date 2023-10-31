<script>
    import M_tabs from "$lib/components/molecules/M_tabs.svelte";
    import M_table from "$lib/components/molecules/table/M_table.svelte";
    import M_tableHeaderItem from "$lib/components/molecules/table/M_tableHeaderItem.svelte";
    import M_tableBodyItem from "$lib/components/molecules/table/M_tableBodyItem.svelte";
    import A_icon from "$lib/components/atoms/A_icon.svelte";
    import {Tooltip} from "svelte-tooltip-simple";
    import A_indicator from "$lib/components/atoms/A_indicator.svelte";
    import O_historyTable from "$lib/components/organisms/O_historyTable.svelte";

    let tabItems = ['Les derniers batch', 'Batch en cours', 'Mes batchs suivis', 'Mes dernières erreurs de batch']
    let activeTabItem = 'Les derniers batch';

    const triggerTabChange = (event) => {
        activeTabItem = event.detail;
    }
</script>

<div class="historyView grid-y">

    <div class="historyView-title cell shrink grid-x align-middle">
        Historique
    </div>

    <div class="historyView-content cell auto grid-y">
        <M_tabs tabItems={tabItems} activeItem={activeTabItem} on:tabChange={triggerTabChange}/>

        <div class="cell auto grid-y">
            {#if activeTabItem === 'Les derniers batch'}
                <O_historyTable></O_historyTable>
            {:else if activeTabItem === 'Batch en cours'}
                <O_historyTable></O_historyTable>
            {:else if activeTabItem === 'Mes batchs suivis'}
                <O_historyTable></O_historyTable>
            {:else if activeTabItem === 'Mes dernières erreurs de batch'}
                <O_historyTable></O_historyTable>
            {/if}
        </div>
    </div>
</div>

<style lang="scss">
  @import "src/app";

  .historyView {
    height: 100%;

    &-title {
      margin-bottom: rem-calc(32px);
      font-size: rem-calc(24px);
    }

    &-content {
      overflow: hidden;
    }
  }
</style>
