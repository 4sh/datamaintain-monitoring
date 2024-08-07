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
    import type {ExecutionDetail} from "$lib/domain/execution/Execution";

    let executionPromise: Promise<ExecutionDetail>

    let tabItems = ['Script', 'Logs']
    let activeTabItem = 'Script'

    const triggerTabChange = (event) => {
        activeTabItem = event.detail;
    }

    $: if($page.params?.executionId) {
        executionPromise = ExecutionService.byId($page.params.executionId);
    }


    let codeLog = 'MongoDB shell version v4.2.21\n' +
        '                    connecting to: mongodb://cluster-prod-k8s-shard-00-00.lk5ez.mongodb.net:27017,cluster-prod-k8s-shard-00-01.lk5ez.mongodb.net:27017,cluster-prod-k8s-shard-00-02.lk5ez.mongodb.net:27017/cartoborne?authSource=admin&compressors=disabled&gssapiServiceName=mongodb&replicaSet=atlas-qhubug-shard-0&ssl=true\n' +
        '                    2023-02-01T14:56:22.439+0000 I  NETWORK  [js] Starting new replica set monitor for atlas-qhubug-shard-0/cluster-prod-k8s-shard-00-00.lk5ez.mongodb.net:27017,cluster-prod-k8s-shard-00-01.lk5ez.mongodb.net:27017,cluster-prod-k8s-shard-00-02.lk5ez.mongodb.net:27017\n' +
        '                    2023-02-01T14:56:22.439+0000 I  CONNPOOL [ReplicaSetMonitor-TaskExecutor] Connecting to cluster-prod-k8s-shard-00-02.lk5ez.mongodb.net:27017\n' +
        '                    2023-02-01T14:56:22.439+0000 I  CONNPOOL [ReplicaSetMonitor-TaskExecutor] Connecting to cluster-prod-k8s-shard-00-00.lk5ez.mongodb.net:27017\n' +
        '                    2023-02-01T14:56:22.439+0000 I  CONNPOOL [ReplicaSetMonitor-TaskExecutor] Connecting to cluster-prod-k8s-shard-00-01.lk5ez.mongodb.net:27017\n' +
        '                    2023-02-01T14:56:22.472+0000 I  NETWORK  [ReplicaSetMonitor-TaskExecutor] Confirmed replica set for atlas-qhubug-shard-0 is atlas-qhubug-shard-0/cluster-prod-k8s-shard-00-00.lk5ez.mongodb.net:27017,cluster-prod-k8s-shard-00-01.lk5ez.mongodb.net:27017,cluster-prod-k8s-shard-00-02.lk5ez.mongodb.net:27017\n' +
        '                    Implicit session: session\n' +
        '                    MongoDB server version: 4.2.23\n' +
        '                    100 owners setted\n' +
        '                    200 owners setted\n' +
        '                    300 owners setted\n' +
        '                    400 owners setted\n' +
        '                    500 owners setted\n' +
        '                    600 owners setted\n' +
        '                    700 owners setted\n' +
        '                    800 owners setted\n' +
        '                    900 owners setted\n' +
        '                    1000 owners setted\n' +
        '                    1100 owners setted\n' +
        '                    1200 owners setted\n' +
        '                    1300 owners setted\n' +
        '                    1400 owners setted\n' +
        '                    1500 owners setted\n' +
        '                    1600 owners setted\n' +
        '                    1700 owners setted\n' +
        '                    1800 owners setted\n' +
        '                    1900 owners setted\n' +
        '                    2000 owners setted\n' +
        '                    2100 owners setted\n' +
        '                    2200 owners setted\n' +
        '                    2300 owners setted\n' +
        '                    2400 owners setted\n' +
        '                    2500 owners setted\n' +
        '                    2600 owners setted\n' +
        '                    2700 owners setted\n' +
        '                    2800 owners setted\n' +
        '                    2900 owners setted\n' +
        '                    3000 owners setted\n' +
        '                    3100 owners setted\n' +
        '                    3200 owners setted\n' +
        '                    3300 owners setted\n' +
        '                    3400 owners setted\n' +
        '                    3500 owners setted\n' +
        '                    3600 owners setted\n' +
        '                    3700 owners setted\n' +
        '                    3800 owners setted\n' +
        '                    3900 owners setted\n' +
        '                    4000 owners setted\n' +
        '                    4100 owners setted\n' +
        '                    4200 owners setted\n' +
        '                    4300 owners setted\n' +
        '                    4400 owners setted\n' +
        '                    4500 owners setted\n' +
        '                    4600 owners setted\n' +
        '                    4700 owners setted\n' +
        '                    4800 owners setted\n' +
        '                    4900 owners setted\n' +
        '                    5000 owners setted\n' +
        '                    5100 owners setted\n' +
        '                    5200 owners setted\n' +
        '                    5300 owners setted\n' +
        '                    5400 owners setted\n' +
        '                    5500 owners setted\n' +
        '                    5600 owners setted\n' +
        '                    5700 owners setted\n' +
        '                    5800 owners setted\n' +
        '                    5900 owners setted\n' +
        '                    6000 owners setted\n' +
        '                    6100 owners setted\n' +
        '                    6200 owners setted\n' +
        '                    6300 owners setted\n' +
        '                    6400 owners setted\n' +
        '                    6500 owners setted\n' +
        '                    6600 owners setted\n' +
        '                    6700 owners setted\n' +
        '                    6800 owners setted\n' +
        '                    6900 owners setted\n' +
        '                    7000 owners setted\n' +
        '                    7100 owners setted\n' +
        '                    7200 owners setted\n' +
        '                    7300 owners setted\n' +
        '                    7400 owners setted\n' +
        '                    7500 owners setted\n' +
        '                    7600 owners setted\n' +
        '                    7700 owners setted\n' +
        '                    7800 owners setted\n' +
        '                    7900 owners setted\n' +
        '                    8000 owners setted\n' +
        '                    8100 owners setted\n' +
        '                    8200 owners setted\n' +
        '                    8300 owners setted\n' +
        '                    8400 owners setted\n' +
        '                    8500 owners setted\n' +
        '                    8600 owners setted\n' +
        '                    8700 owners setted\n' +
        '                    8800 owners setted\n' +
        '                    8900 owners setted\n' +
        '                    9000 owners setted\n' +
        '                    9100 owners setted\n' +
        '                    9200 owners setted\n' +
        '                    9300 owners setted\n' +
        '                    9400 owners setted\n' +
        '                    9500 owners setted\n' +
        '                    9600 owners setted\n' +
        '                    9700 owners setted\n' +
        '                    9800 owners setted\n' +
        '                    9900 owners setted\n' +
        '                    10000 owners setted\n' +
        '                    10100 owners setted\n' +
        '                    10200 owners setted\n' +
        '                    10300 owners setted\n' +
        '                    10400 owners setted\n' +
        '                    10500 owners setted\n' +
        '                    10600 owners setted\n' +
        '                    10700 owners setted\n' +
        '                    10800 owners setted\n' +
        '                    10900 owners setted\n' +
        '                    11000 owners setted\n' +
        '                    11100 owners setted\n' +
        '                    11200 owners setted\n' +
        '                    11300 owners setted\n' +
        '                    11400 owners setted\n' +
        '                    11500 owners setted\n' +
        '                    11600 owners setted\n' +
        '                    11700 owners setted\n' +
        '                    11800 owners setted\n' +
        '                    11900 owners setted\n' +
        '                    12000 owners setted\n' +
        '                    12100 owners setted\n' +
        '                    12200 owners setted\n' +
        '                    12300 owners setted\n' +
        '                    12400 owners setted\n' +
        '                    12500 owners setted\n' +
        '                    12600 owners setted\n' +
        '                    12700 owners setted\n' +
        '                    12800 owners setted\n' +
        '                    12900 owners setted\n' +
        '                    13000 owners setted\n' +
        '                    13100 owners setted\n' +
        '                    13200 owners setted\n' +
        '                    13300 owners setted\n' +
        '                    13400 owners setted\n' +
        '                    13500 owners setted\n' +
        '                    13600 owners setted\n' +
        '                    13700 owners setted\n' +
        '                    13800 owners setted\n' +
        '                    13900 owners setted\n' +
        '                    14000 owners setted\n' +
        '                    14100 owners setted\n' +
        '                    14200 owners setted\n' +
        '                    14300 owners setted\n' +
        '                    14400 owners setted\n' +
        '                    14500 owners setted\n' +
        '                    14600 owners setted\n' +
        '                    14700 owners setted\n' +
        '                    14800 owners setted\n' +
        '                    14900 owners setted\n' +
        '                    15000 owners setted\n' +
        '                    15100 owners setted\n' +
        '                    15200 owners setted\n' +
        '                    15300 owners setted\n' +
        '                    15400 owners setted\n' +
        '                    15500 owners setted\n' +
        '                    15600 owners setted\n' +
        '                    15700 owners setted\n' +
        '                    15800 owners setted\n' +
        '                    15900 owners setted\n' +
        '                    16000 owners setted\n' +
        '                    16100 owners setted\n' +
        '                    16200 owners setted\n' +
        '                    16300 owners setted\n' +
        '                    16400 owners setted\n' +
        '                    16500 owners setted\n' +
        '                    16600 owners setted\n' +
        '                    16700 owners setted\n' +
        '                    16800 owners setted\n' +
        '                    16900 owners setted\n' +
        '                    17000 owners setted\n' +
        '                    17100 owners setted\n' +
        '                    17200 owners setted\n' +
        '                    17300 owners setted\n' +
        '                    17400 owners setted\n' +
        '                    17500 owners setted\n' +
        '                    17600 owners setted\n' +
        '                    17700 owners setted\n' +
        '                    17800 owners setted\n' +
        '                    17900 owners setted\n' +
        '                    18000 owners setted\n' +
        '                    18100 owners setted\n' +
        '                    18200 owners setted\n' +
        '                    18300 owners setted\n' +
        '                    18400 owners setted\n' +
        '                    18500 owners setted\n' +
        '                    18600 owners setted\n' +
        '                    18700 owners setted\n' +
        '                    18800 owners setted\n' +
        '                    18900 owners setted\n' +
        '                    19000 owners setted\n' +
        '                    19100 owners setted\n' +
        '                    19200 owners setted\n' +
        '                    19300 owners setted\n' +
        '                    19400 owners setted\n' +
        '                    19500 owners setted\n' +
        '                    19600 owners setted\n' +
        '                    19700 owners setted\n' +
        '                    19800 owners setted\n' +
        '                    19900 owners setted\n' +
        '                    20000 owners setted\n' +
        '                    20100 owners setted\n' +
        '                    20200 owners setted\n' +
        '                    20300 owners setted\n' +
        '                    20400 owners setted\n' +
        '                    20500 owners setted\n' +
        '                    20600 owners setted\n' +
        '                    20700 owners setted\n' +
        '                    20800 owners setted\n' +
        '                    20900 owners setted\n' +
        '                    21000 owners setted\n' +
        '                    21100 owners setted\n' +
        '                    21200 owners setted\n' +
        '                    21300 owners setted\n' +
        '                    21400 owners setted\n' +
        '                    21500 owners setted\n' +
        '                    21600 owners setted\n' +
        '                    21700 owners setted\n' +
        '                    21800 owners setted\n' +
        '                    21900 owners setted\n' +
        '                    22000 owners setted\n' +
        '                    22100 owners setted\n' +
        '                    22200 owners setted\n' +
        '                    22300 owners setted\n' +
        '                    22400 owners setted\n' +
        '                    22500 owners setted\n' +
        '                    22600 owners setted\n' +
        '                    22700 owners setted\n' +
        '                    22800 owners setted\n' +
        '                    22900 owners setted\n' +
        '                    23000 owners setted\n' +
        '                    23100 owners setted\n' +
        '                    23200 owners setted\n' +
        '                    23300 owners setted\n' +
        '                    23400 owners setted\n' +
        '                    23500 owners setted\n' +
        '                    23600 owners setted\n' +
        '                    23700 owners setted\n' +
        '                    23800 owners setted\n' +
        '                    23900 owners setted\n' +
        '                    24000 owners setted\n' +
        '                    24100 owners setted\n' +
        '                    24200 owners setted\n' +
        '                    24300 owners setted\n' +
        '                    24400 owners setted\n' +
        '                    24500 owners setted\n' +
        '                    24600 owners setted\n' +
        '                    24700 owners setted\n' +
        '                    24800 owners setted\n' +
        '                    24900 owners setted\n' +
        '                    25000 owners setted\n' +
        '                    25100 owners setted\n' +
        '                    25200 owners setted\n' +
        '                    25300 owners setted\n' +
        '                    25400 owners setted\n' +
        '                    25500 owners setted\n' +
        '                    25600 owners setted\n' +
        '                    25700 owners setted\n' +
        '                    25800 owners setted\n' +
        '                    25900 owners setted\n' +
        '                    26000 owners setted\n' +
        '                    26100 owners setted\n' +
        '                    26200 owners setted\n' +
        '                    26300 owners setted\n' +
        '                    26400 owners setted\n' +
        '                    26500 owners setted\n' +
        '                    26600 owners setted\n' +
        '                    26700 owners setted\n' +
        '                    26800 owners setted\n' +
        '                    26900 owners setted\n' +
        '                    27000 owners setted\n' +
        '                    27100 owners setted\n' +
        '                    27200 owners setted\n' +
        '                    27300 owners setted\n' +
        '                    27400 owners setted\n' +
        '                    27500 owners setted\n' +
        '                    27600 owners setted\n' +
        '                    27700 owners setted\n' +
        '                    27800 owners setted\n' +
        '                    27900 owners setted\n' +
        '                    28000 owners setted\n' +
        '                    28100 owners setted\n' +
        '                    28200 owners setted\n' +
        '                    28300 owners setted\n' +
        '                    28400 owners setted\n' +
        '                    28500 owners setted\n' +
        '                    28600 owners setted\n' +
        '                    28700 owners setted\n' +
        '                    28800 owners setted\n' +
        '                    28900 owners setted\n' +
        '                    29000 owners setted\n' +
        '                    29100 owners setted\n' +
        '                    29200 owners setted\n' +
        '                    29300 owners setted\n' +
        '                    29400 owners setted\n' +
        '                    29500 owners setted\n' +
        '                    29600 owners setted\n' +
        '                    29700 owners setted\n' +
        '                    29800 owners setted\n' +
        '                    29900 owners setted\n' +
        '                    30000 owners setted\n' +
        '                    30100 owners setted\n' +
        '                    30200 owners setted\n' +
        '                    30300 owners setted\n' +
        '                    30400 owners setted\n' +
        '                    30500 owners setted\n' +
        '                    30600 owners setted\n' +
        '                    30700 owners setted\n' +
        '                    30800 owners setted\n' +
        '                    30900 owners setted\n' +
        '                    31000 owners setted\n' +
        '                    31100 owners setted\n' +
        '                    31200 owners setted\n' +
        '                    31300 owners setted\n' +
        '                    31400 owners setted\n' +
        '                    31500 owners setted\n' +
        '                    31600 owners setted\n' +
        '                    31700 owners setted\n' +
        '                    31800 owners setted\n' +
        '                    31900 owners setted\n' +
        '                    32000 owners setted\n' +
        '                    32100 owners setted\n' +
        '                    32200 owners setted\n' +
        '                    32300 owners setted\n' +
        '                    32400 owners setted\n' +
        '                    32500 owners setted\n' +
        '                    32600 owners setted\n' +
        '                    32700 owners setted\n' +
        '                    32800 owners setted\n' +
        '                    32900 owners setted\n' +
        '                    33000 owners setted\n' +
        '                    33100 owners setted\n' +
        '                    33200 owners setted\n' +
        '                    33300 owners setted\n' +
        '                    33400 owners setted\n' +
        '                    33500 owners setted\n' +
        '                    33600 owners setted\n' +
        '                    33700 owners setted\n' +
        '                    33800 owners setted\n' +
        '                    33900 owners setted\n' +
        '                    34000 owners setted\n' +
        '                    34100 owners setted\n' +
        '                    34200 owners setted\n' +
        '                    34300 owners setted\n' +
        '                    34400 owners setted\n' +
        '                    34500 owners setted\n' +
        '                    34600 owners setted\n' +
        '                    34700 owners setted\n' +
        '                    34800 owners setted\n' +
        '                    34900 owners setted\n' +
        '                    35000 owners setted\n' +
        '                    35100 owners setted\n' +
        '                    35200 owners setted\n' +
        '                    35300 owners setted\n' +
        '                    35400 owners setted\n' +
        '                    35500 owners setted\n' +
        '                    35600 owners setted\n' +
        '                    35700 owners setted\n' +
        '                    35800 owners setted\n' +
        '                    35900 owners setted\n' +
        '                    36000 owners setted\n' +
        '                    36100 owners setted\n' +
        '                    36200 owners setted\n' +
        '                    36300 owners setted\n' +
        '                    36400 owners setted\n' +
        '                    36500 owners setted\n' +
        '                    36600 owners setted\n' +
        '                    36700 owners setted\n' +
        '                    36800 owners setted\n' +
        '                    36900 owners setted\n' +
        '                    37000 owners setted\n' +
        '                    37100 owners setted\n' +
        '                    37200 owners setted\n' +
        '                    37300 owners setted\n' +
        '                    37400 owners setted\n' +
        '                    37500 owners setted\n' +
        '                    37600 owners setted\n' +
        '                    37700 owners setted\n' +
        '                    37800 owners setted\n' +
        '                    37900 owners setted\n' +
        '                    38000 owners setted\n' +
        '                    38100 owners setted\n' +
        '                    38200 owners setted\n' +
        '                    38300 owners setted\n' +
        '                    38400 owners setted\n' +
        '                    38500 owners setted\n' +
        '                    38600 owners setted\n' +
        '                    38700 owners setted\n' +
        '                    38800 owners setted\n' +
        '                    38900 owners setted\n' +
        '                    39000 owners setted\n' +
        '                    39100 owners setted\n' +
        '                    39200 owners setted\n' +
        '                    39300 owners setted\n' +
        '                    39400 owners setted\n' +
        '                    39500 owners setted\n' +
        '                    39600 owners setted\n' +
        '                    39700 owners setted\n' +
        '                    39800 owners setted\n' +
        '                    39900 owners setted\n' +
        '                    40000 owners setted\n' +
        '                    40100 owners setted\n' +
        '                    40200 owners setted\n' +
        '                    40300 owners setted\n' +
        '                    40400 owners setted\n' +
        '                    40500 owners setted\n' +
        '                    40600 owners setted\n' +
        '                    40700 owners setted\n' +
        '                    40800 owners setted\n' +
        '                    40900 owners setted\n' +
        '                    41000 owners setted\n' +
        '                    41100 owners setted\n' +
        '                    41200 owners setted\n' +
        '                    41300 owners setted\n' +
        '                    41400 owners setted\n' +
        '                    41500 owners setted\n' +
        '                    41600 owners setted\n' +
        '                    41621 owners setted';
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
                <O_scriptList></O_scriptList>
            </div>
        </div>

        <div class="executionView-container cell shrink grid-y">
            <M_tabs tabItems={tabItems} activeItem={activeTabItem} on:tabChange={triggerTabChange}/>

            <div class="executionView-details cell auto grid-y">
                {#if activeTabItem === 'Script'}
                    <O_scriptDetail></O_scriptDetail>
                {:else if activeTabItem === 'Logs'}
                    <M_codeBlock codeLog="{codeLog}"></M_codeBlock>
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
