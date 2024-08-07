<script lang="ts">
    import A_icon from "$lib/components/atoms/A_icon.svelte";
    import A_tag from "$lib/components/atoms/form/A_tag.svelte";
    import type {ScriptExecutionDetail} from "$lib/domain/execution/Execution";
    import {ExecutionStatus} from '$lib/domain/execution/Execution';

    export let scriptExecution: ScriptExecutionDetail
</script>

<div class="oScript cell auto">
    <div class="oScript-mainTitle">
        {scriptExecution.script.name}
    </div>
    <div class=" oScript-container grid-x align-middle">
        <div class="oScript-title cell shrink">
            Statut :
        </div>
        <div class="oScript-data cell shrink">
            {scriptExecution.status}
        </div>
        <div class="oScript-icon cell shrink grid-x align-middle align-center">
            <A_icon type="autorenew" size="light"></A_icon>
        </div>
        <div class="oScript-linkScript cell auto grid-x align-right align-middle">
            <div class="cell shrink">Accéder au script</div>
            <div class="cell shrink">
                <A_icon type="chevron_right" size="extraThin"></A_icon>
            </div>
        </div>
    </div>
    <div class=" oScript-container grid-x align-middle">
        <div class="oScript-title cell shrink">
            Date de début d’exécution :
        </div>
        <div class="oScript-data cell shrink">
            {scriptExecution.startDate.toLocaleDateString()}
        </div>
    </div>
    <div class=" oScript-container grid-x align-middle">
        <div class="oScript-title cell shrink">
            Temps d’exécution :
        </div>
        <div class="oScript-data cell shrink">
            {scriptExecution.durationInMs} ms
        </div>
    </div>
    <div class=" oScript-container grid-x align-middle">
        <div class="oScript-title cell shrink">
            Date de fin d’exécution :
        </div>
        <div class="oScript-data cell shrink">
            {#if scriptExecution.status === ExecutionStatus.COMPLETED || scriptExecution.status === ExecutionStatus.ERROR}
                {scriptExecution.endDate.toLocaleDateString()}
            {:else}
                En cours
            {/if}
        </div>
    </div>
    <div class=" oScript-container grid-x align-middle _column">
        <div class="oScript-title cell shrink">
            Flags :
        </div>
        <div class="oScript-data cell shrink grid-x align-middle">
            <div class="oScript-tag cell shrink">
                <A_tag label="Flag 1"></A_tag>
            </div>
        </div>
    </div>
    <div class=" oScript-container grid-x align-middle _column">
        <div class="oScript-title cell shrink">
            Tags :
        </div>
        <div class="oScript-data cell shrink grid-x align-middle">
            <div class="oScript-tag cell shrink">
                <A_tag label="Tag 1"></A_tag>
            </div>
            <div class="oScript-tag cell shrink">
                <A_tag label="Tag 2"></A_tag>
            </div>
        </div>
    </div>
    <div class=" oScript-container grid-x align-middle _column">
        <div class="oScript-title cell shrink">
            Chemin d’exécution :
        </div>
        <div class="oScript-url cell shrink">
            truc/muchmachin/compartimentD/menuW/dossierbidule/sousdossierchose/fichiertartempion
        </div>
    </div>
    <div class=" oScript-container grid-x align-middle _column">
        <div class="oScript-title cell shrink">
            Paramètres d’exécution :
        </div>
        <div class="oScript-url cell shrink">
            settings
        </div>
    </div>
</div>

<style lang="scss">
  @import "src/app";

  .oScript {
    border: rem-calc(1px) solid rgba($app-secondary_700, .5);
    border-radius: rem-calc(4px);
    background: $app-primary_900;
    padding: rem-calc(20px);
    font-size: rem-calc(13px);
    height: calc(100% - 52px);

    &-mainTitle {
      font-weight: 500;
      text-transform: uppercase;
      font-size: rem-calc(13px);
      margin-bottom: rem-calc(15px);
      padding-bottom: rem-calc(15px);
      border-bottom: rem-calc(1px) solid rgba($app-secondary_700, .5);
    }

    &-container {
      line-height: rem-calc(17px);
      margin-bottom: rem-calc(15px);

      &:first-child {
        height: rem-calc(16px);
      }

      &:last-child {
        margin-bottom: 0;
      }

      &._column {
        flex-direction: column;
        align-items: flex-start;

        .oScript-title {
          margin-bottom: rem-calc(5px);
        }
      }
    }

    &-linkScript {
      color: $app-primary_700;
      font-weight: 500;

      > div {
        height: rem-calc(16px);
      }

      &:hover {
        cursor: pointer;
        text-decoration: underline;
      }
    }

    &-title {
      font-weight: 600;
      margin-right: rem-calc(8px);
    }

    &-icon {
      height: rem-calc(30px);
      width: rem-calc(30px);
      margin-left: rem-calc(10px);
      color: $app-primary_700;
      background: rgba(var(--secondary-color-rgb)/0.1);
      border-radius: rem-calc(15px);
    }

    &-url {
      word-wrap: break-word;
      background: rgba(var(--secondary-color-rgb)/0.1);
      padding: rem-calc(5px 10px);
      width: calc(100% - 20px);
    }

    &-tag {
      margin-right: rem-calc(8px);
    }
  }
</style>
