<script lang="ts">
    import {ProjectService} from "$lib/services/ProjectService";
    import {page} from "$app/stores";
    import {ExecutionMock} from "$lib/mocks/ExecutionMock";
    import O_matrixModuleEnv from "$lib/components/organisms/O_matrix/O_matrixModuleEnv.svelte";
    import M_error from "$lib/components/molecules/M_error.svelte";
    import A_link from '$lib/components/atoms/A_link.svelte';

    let projectPromise
   let moduleEnvMatrix

   $: if($page.params?.project) {
       projectPromise = ProjectService.byId($page.params.project);
       moduleEnvMatrix = ExecutionMock.moduleEnvMatrixByProject($page.params.project)
   }
</script>

{#await projectPromise}
    <p>...waiting</p>
{:then project}
    <div class="grid-x">
        <div class="cell auto projectOverview-title">
            Project {project.name}
        </div>

        <div class="cell shrink projectOverview-action">
            <A_link href="{project.id}/modules">Nouveau module</A_link>
        </div>

        <div class="cell shrink projectOverview-action">
            <A_link href="{project.id}/envs">Nouvel environnement</A_link>
        </div>
        <div class="cell shrink projectOverview-action">
            <A_link href="{project.id}/edit" id="editProject">Editer</A_link>
        </div>
    </div>

    {#if moduleEnvMatrix}
        <O_matrixModuleEnv projectRef="{project.id}" matrix="{moduleEnvMatrix}"></O_matrixModuleEnv>
    {/if}
{:catch error}
    <M_error>
        Project not found !
    </M_error>
{/await}

<style lang="scss">
  @import "src/app";

  .projectOverview {
    &-title {
      margin-bottom: rem-calc(32px);
      font-size: rem-calc(24px);
    }

    &-action {
      padding-right:rem-calc(12px);
      vertical-align: center;
    }
  }

</style>
