<script>
    import A_icon from "$lib/components/atoms/A_icon.svelte";
    import M_tableBodyItem from "$lib/components/molecules/table/M_tableBodyItem.svelte";
    import M_table from "$lib/components/molecules/table/M_table.svelte";
    import A_indicator from "$lib/components/atoms/A_indicator.svelte";
    import {Tooltip} from "svelte-tooltip-simple";
    import M_tableHeaderItem from "$lib/components/molecules/table/M_tableHeaderItem.svelte";
</script>

<M_table>
    <svelte:fragment slot="headerContent">
        <M_tableHeaderItem></M_tableHeaderItem>
        <M_tableHeaderItem width="44">Statut</M_tableHeaderItem>
        <M_tableHeaderItem>Nom</M_tableHeaderItem>
        <M_tableHeaderItem>Projet</M_tableHeaderItem>
        <M_tableHeaderItem>Env</M_tableHeaderItem>
        <M_tableHeaderItem>Module</M_tableHeaderItem>
        <M_tableHeaderItem>Début d'éxecution</M_tableHeaderItem>
        <M_tableHeaderItem>Temps d'éxecution</M_tableHeaderItem>
        <M_tableHeaderItem textAlign="center" width="47">Détails</M_tableHeaderItem>
    </svelte:fragment>
    <svelte:fragment slot="bodyContent">
        <tr class="_inProgress">
            <M_tableBodyItem width="30" textAlign="right">
                <Tooltip text="Retirer des favoris">
                    <A_icon type="star" size="semiLight"></A_icon>
                </Tooltip>
            </M_tableBodyItem>
            <M_tableBodyItem width="44">
                <div class="tableIcon">
                    <A_icon type="autorenew"></A_icon>
                </div>
            </M_tableBodyItem>
            <M_tableBodyItem>
                BeC13092022-1
            </M_tableBodyItem>
            <M_tableBodyItem>
                <A_indicator label="CIN"></A_indicator>
            </M_tableBodyItem>
            <M_tableBodyItem>
                <A_indicator label="R"></A_indicator>
            </M_tableBodyItem>
            <M_tableBodyItem>
                Module 3
            </M_tableBodyItem>
            <M_tableBodyItem>
                02/08/2022 à 18h57
            </M_tableBodyItem>
            <M_tableBodyItem>
                1h 2 min 34 sec
            </M_tableBodyItem>
            <M_tableBodyItem textAlign="center" width="47">
                <Tooltip text="Voir le détail">
                    <A_icon type="visibility" size="semiLight"></A_icon>
                </Tooltip>
            </M_tableBodyItem>
        </tr>
        <tr class="_error">
            <M_tableBodyItem width="30" textAlign="right">
                <Tooltip text="Ajouter aux favoris">
                    <A_icon type="star_border" size="semiLight"></A_icon>
                </Tooltip>
            </M_tableBodyItem>
            <M_tableBodyItem width="44">
                <div class="tableIcon">
                    <A_icon type="error_outline"></A_icon>
                </div>
            </M_tableBodyItem>
            <M_tableBodyItem>
                BeC13092022-1
            </M_tableBodyItem>
            <M_tableBodyItem>
                <A_indicator label="CIN"></A_indicator>
            </M_tableBodyItem>
            <M_tableBodyItem>
                <A_indicator label="R"></A_indicator>
            </M_tableBodyItem>
            <M_tableBodyItem>
                Module 3
            </M_tableBodyItem>
            <M_tableBodyItem>
                02/08/2022 à 18h57
            </M_tableBodyItem>
            <M_tableBodyItem>
                1h 2 min 34 sec
            </M_tableBodyItem>
            <M_tableBodyItem textAlign="center" width="47">
                <Tooltip text="Voir le détail">
                    <A_icon type="visibility" size="semiLight"></A_icon>
                </Tooltip>
            </M_tableBodyItem>
        </tr>
        <tr class="_inProgress">
            <M_tableBodyItem width="30" textAlign="right">
                <Tooltip text="Ajouter aux favoris">
                    <A_icon type="star_border" size="semiLight"></A_icon>
                </Tooltip>
            </M_tableBodyItem>
            <M_tableBodyItem width="44">
                <div class="tableIcon">
                    <A_icon type="autorenew"></A_icon>
                </div>
            </M_tableBodyItem>
            <M_tableBodyItem>
                BeC13092022-1
            </M_tableBodyItem>
            <M_tableBodyItem>
                <A_indicator label="CIN"></A_indicator>
            </M_tableBodyItem>
            <M_tableBodyItem>
                <A_indicator label="R"></A_indicator>
            </M_tableBodyItem>
            <M_tableBodyItem>
                Module 3
            </M_tableBodyItem>
            <M_tableBodyItem>
                02/08/2022 à 18h57
            </M_tableBodyItem>
            <M_tableBodyItem>
                1h 2 min 34 sec
            </M_tableBodyItem>
            <M_tableBodyItem textAlign="center" width="47">
                <Tooltip text="Voir le détail">
                    <A_icon type="visibility" size="semiLight"></A_icon>
                </Tooltip>
            </M_tableBodyItem>
        </tr>
        <tr class="_completed">
            <M_tableBodyItem width="30" textAlign="right">
                <Tooltip text="Ajouter aux favoris">
                    <A_icon type="star_border" size="semiLight"></A_icon>
                </Tooltip>
            </M_tableBodyItem>
            <M_tableBodyItem width="44">
                <div class="tableIcon">
                    <A_icon type="task_alt"></A_icon>
                </div>
            </M_tableBodyItem>
            <M_tableBodyItem>
                BeC13092022-1
            </M_tableBodyItem>
            <M_tableBodyItem>
                <A_indicator label="CIN"></A_indicator>
            </M_tableBodyItem>
            <M_tableBodyItem>
                <A_indicator label="R"></A_indicator>
            </M_tableBodyItem>
            <M_tableBodyItem>
                Module 3
            </M_tableBodyItem>
            <M_tableBodyItem>
                02/08/2022 à 18h57
            </M_tableBodyItem>
            <M_tableBodyItem>
                1h 2 min 34 sec
            </M_tableBodyItem>
            <M_tableBodyItem textAlign="center" width="47">
                <Tooltip text="Voir le détail">
                    <A_icon type="visibility" size="semiLight"></A_icon>
                </Tooltip>
            </M_tableBodyItem>
        </tr>
    </svelte:fragment>
</M_table>

<style lang="scss">
  @import "src/app";

  tr {
    &:nth-child(even) {
      background-color: rgb(var(--secondary-color-rgb) / .05);
    }

    &:hover {
      background-color: rgb(var(--secondary-color-rgb) / .1);
    }

    &._completed {
      .tableIcon {
        color: $app-success_900;
      }
    }

    &._inProgress {
      .tableIcon {
        color: $app-primary_700;
      }
    }

    &._error {
      .tableIcon {
        color: $app-error_900;
      }
    }
  }

</style>
