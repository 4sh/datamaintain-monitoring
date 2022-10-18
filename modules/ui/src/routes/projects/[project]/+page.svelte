<script lang="ts">
   import {ProjectService} from "../../../lib/services/ProjectService";
   import {page} from "$app/stores";

   let project

   $: if($page.params?.project) {
       project = ProjectService.byId($page.params.project);
   }
</script>

Project {project.name}
