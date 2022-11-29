<script lang="ts">
   import {ProjectService} from "../../../lib/services/ProjectService";
   import {page} from "$app/stores";
   import {ExecutionMock} from "../../../lib/mocks/ExecutionMock";
   import ModuleEnvMatrixComponent from "../../../lib/components/matrix/ModuleEnvMatrixComponent.svelte";

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
        <ModuleEnvMatrixComponent projectRef="{project.id}" matrix="{moduleEnvMatrix}"></ModuleEnvMatrixComponent>
    {/if}
{:catch error}
    <p style="color: red">Project not found !</p>
{/await}