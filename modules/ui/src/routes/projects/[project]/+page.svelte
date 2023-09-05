<script lang="ts">
   import {ProjectService} from "$lib/services/ProjectService";
   import {page} from "$app/stores";
   import {ExecutionMock} from "$lib/mocks/ExecutionMock";
   import O_matrixModuleEnv from "$lib/components/organisms/O_matrix/O_matrixModuleEnv.svelte";
   import M_error from "$lib/components/molecules/M_error.svelte";

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
    Project {project.name}
    
    <br>
    <a href="{project.id}/modules">Créer un nouveau module</a>

    <br>
    <a href="{project.id}/envs">Créer un nouvel environnement</a>

    <br>
    <a href="{project.id}/edit">Editer</a>

    {#if moduleEnvMatrix}
        <O_matrixModuleEnv projectRef="{project.id}" matrix="{moduleEnvMatrix}"></O_matrixModuleEnv>
    {/if}
{:catch error}
    <M_error>
        Project not found !
    </M_error>
{/await}
