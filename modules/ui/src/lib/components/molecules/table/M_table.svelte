<script>
    import A_icon from "$lib/components/atoms/A_icon.svelte";
    import A_indicator from "$lib/components/atoms/A_indicator.svelte";
    import {Tooltip} from "svelte-tooltip-simple";
</script>

<table>
    <thead>
    <tr>
        <slot name="headerContent"></slot>
    </tr>

    <!--        <th></th>-->
    <!--        <th>Statut</th>-->
    <!--        <th>Nom</th>-->
    <!--        <th>Projet</th>-->
    <!--        <th>Env</th>-->
    <!--        <th>Module</th>-->
    <!--        <th>Début d'éxecution</th>-->
    <!--        <th>Temps d'éxecution</th>-->
    <!--        <th>Détails</th>-->
    </thead>
    <tbody>
    <slot name="bodyContent"></slot>
    </tbody>

    <!--    <tr class="_completed">-->
    <!--        <td>-->
    <!--            <Tooltip text="Ajouter aux favoris">-->
    <!--                <A_icon type="star_border" size="semiLight"></A_icon>-->
    <!--            </Tooltip>-->
    <!--        </td>-->
    <!--        <td>-->
    <!--            <A_icon type="task_alt"></A_icon>-->
    <!--        </td>-->
    <!--        <td>BeC13092022-1</td>-->
    <!--        <td>-->
    <!--            <A_indicator label="CIN"></A_indicator>-->
    <!--        </td>-->
    <!--        <td>-->
    <!--            <A_indicator label="R"></A_indicator>-->
    <!--        </td>-->
    <!--        <td>Module 3</td>-->
    <!--        <td>02/08/2022 à 18h57</td>-->
    <!--        <td>1h 2 min 34 sec</td>-->
    <!--        <td>-->
    <!--            <Tooltip text="Voir le détail">-->
    <!--                <A_icon type="visibility" size="semiLight"></A_icon>-->
    <!--            </Tooltip>-->
    <!--        </td>-->
    <!--    </tr>-->
    <!--    <tr class="_inProgress">-->
    <!--        <td>-->
    <!--            <Tooltip text="Retirer des favoris">-->
    <!--                <A_icon type="star" size="semiLight"></A_icon>-->
    <!--            </Tooltip>-->
    <!--        </td>-->
    <!--        <td>-->
    <!--            <A_icon type="autorenew"></A_icon>-->
    <!--        </td>-->
    <!--        <td>CHE12092022-1</td>-->
    <!--        <td>-->
    <!--            <A_indicator label="CHE"></A_indicator>-->
    <!--        </td>-->
    <!--        <td>-->
    <!--            <A_indicator label="PP"></A_indicator>-->
    <!--        </td>-->
    <!--        <td>Module 6</td>-->
    <!--        <td>13/09/2022 à 18h57</td>-->
    <!--        <td>2j 23h 21min 43 sec</td>-->
    <!--        <td>-->
    <!--            <Tooltip text="Voir le détail">-->
    <!--                <A_icon type="visibility" size="semiLight"></A_icon>-->
    <!--            </Tooltip>-->
    <!--        </td>-->
    <!--    </tr>-->
    <!--    <tr class="_completed">-->
    <!--        <td>-->
    <!--            <Tooltip text="Retirer des favoris">-->
    <!--                <A_icon type="star" size="semiLight"></A_icon>-->
    <!--            </Tooltip>-->
    <!--        </td>-->
    <!--        <td>-->
    <!--            <A_icon type="task_alt"></A_icon>-->
    <!--        </td>-->
    <!--        <td>CHE12092022-1</td>-->
    <!--        <td>-->
    <!--            <A_indicator label="CHE"></A_indicator>-->
    <!--        </td>-->
    <!--        <td>-->
    <!--            <A_indicator label="A"></A_indicator>-->
    <!--        </td>-->
    <!--        <td>Module 6</td>-->
    <!--        <td>13/09/2022 à 18h57</td>-->
    <!--        <td>2j 23h 21min 43 sec</td>-->
    <!--        <td>-->
    <!--            <Tooltip text="Voir le détail">-->
    <!--                <A_icon type="visibility" size="semiLight"></A_icon>-->
    <!--            </Tooltip>-->
    <!--        </td>-->
    <!--    </tr>-->
    <!--    <tr class="_error">-->
    <!--        <td>-->
    <!--            <Tooltip text="Ajouter aux favoris">-->
    <!--                <A_icon type="star_border" size="semiLight"></A_icon>-->
    <!--            </Tooltip>-->
    <!--        </td>-->
    <!--        <td>-->
    <!--            <A_icon type="error_outline"></A_icon>-->
    <!--        </td>-->
    <!--        <td>BeC13092022-2</td>-->
    <!--        <td>-->
    <!--            <A_indicator label="PoF"></A_indicator>-->
    <!--        </td>-->
    <!--        <td>-->
    <!--            <A_indicator label="QA"></A_indicator>-->
    <!--        </td>-->
    <!--        <td>Module 6</td>-->
    <!--        <td>13/09/2022 à 18h57</td>-->
    <!--        <td>2j 23h 21min 43 sec</td>-->
    <!--        <td>-->
    <!--            <Tooltip text="Voir le détail">-->
    <!--                <A_icon type="visibility" size="semiLight"></A_icon>-->
    <!--            </Tooltip>-->
    <!--        </td>-->
    <!--    </tr>-->
</table>

<style lang="scss">
  @import "src/app";

  table {
    background-color: rgb(var(--secondary-color-rgb) / .05);
    border-radius: rem-calc(6px);
    border-spacing: inherit;
    overflow: hidden;

    thead {
      background-color: $app-primary_900;
    }
  }
</style>
