<script>
    import A_icon from "$lib/components/atoms/A_icon.svelte";
    import A_tag from "$lib/components/atoms/form/A_tag.svelte";
</script>

<div class="oScript">
    <div class="oScript-mainTitle">
        Script Name
    </div>
    <div class="oScript-container">
        <div class="oScript-title">
            Statut :
        </div>
        <div class="oScript-data">
            En cours
        </div>
        <div class="oScript-icon">
            <A_icon type="autorenew" size="light"></A_icon>
        </div>
        <div class="oScript-linkScript">
            <div>Accéder au script</div>
            <div>
                <A_icon type="chevron_right" size="extraThin"></A_icon>
            </div>
        </div>
    </div>
    <div class="oScript-container">
        <div class="oScript-title">
            Date de début d’exécution :
        </div>
        <div class="oScript-data">
            12/09/2022 à 17h30
        </div>
    </div>
    <div class="oScript-container">
        <div class="oScript-title">
            Temps d’exécution :
        </div>
        <div class="oScript-data">
            2 : 27 : 34
        </div>
    </div>
    <div class="oScript-container">
        <div class="oScript-title">
            Date de fin d’exécution :
        </div>
        <div class="oScript-data">
            En cours
        </div>
    </div>
    <div class="oScript-container _column">
        <div class="oScript-title">
            Flags :
        </div>
        <div class="oScript-data">
            <div class="oScript-tag">
                <A_tag label="Flag 1"></A_tag>
            </div>
        </div>
    </div>
    <div class="oScript-container _column">
        <div class="oScript-title">
            Tags :
        </div>
        <div class="oScript-data">
            <div class="oScript-tag">
                <A_tag label="Tag 1"></A_tag>
            </div>
            <div class="oScript-tag">
                <A_tag label="Tag 2"></A_tag>
            </div>
        </div>
    </div>
    <div class="oScript-container _column">
        <div class="oScript-title">
            Chemin d’exécution :
        </div>
        <div class="oScript-url">
            truc/muchmachin/compartimentD/menuW/dossierbidule/sousdossierchose/fichiertartempion
        </div>
    </div>
    <div class="oScript-container _column">
        <div class="oScript-title">
            Paramètres d’exécution :
        </div>
        <div class="oScript-url">
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
      display: flex;
      align-items: center;
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
      flex: 1 1 0;
      display: flex;
      justify-content: flex-end;
      align-items: center;
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

    &-data {
      display: flex;
    }

    &-icon {
      height: rem-calc(30px);
      width: rem-calc(30px);
      margin-left: rem-calc(10px);
      color: $app-primary_700;
      background: rgba(var(--secondary-color-rgb)/0.1);
      align-items: center;
      display: flex;
      justify-content: center;
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
