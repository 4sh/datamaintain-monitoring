<script lang="ts">
    import {ProjectService} from "$lib/services/ProjectService";
    import {HierarchySearch} from "$lib/domain/ProjectHierarchy";
    import O_hierarchyProject from "$lib/components/organisms/O_hierarchy/O_hierarchyProject.svelte";
    import M_inputSearch from "$lib/components/molecules/M_inputSearch.svelte";
    import {Svroller} from "svrollbar";

    let projectHierarchiesPromise = ProjectService.projectHierarchies();

    function filterHierarchy(event) {
        projectHierarchiesPromise = ProjectService.projectHierarchies().then(result => {
            return new HierarchySearch(event.target.value).filter(result);
        })
    }
</script>

{#await projectHierarchiesPromise}
    <p>...waiting</p>
{:then projectHierarchies}


    <div class="hierarchy">
        <div class="hierarchy-title">
            Modules
        </div>
        <div class="hierarchy-search">
            <M_inputSearch on:change="{filterHierarchy}"/>
        </div>

        <div class="hierarchy-projects">
            <Svroller>
                {#each projectHierarchies as project}
                    <O_hierarchyProject {project}/>
                {/each}
            </Svroller>
        </div>
    </div>


{:catch error}
    <p style="color: red">Env not found !</p>
{/await}

<style lang="scss">
  @import "src/app";

  .hierarchy {
    display: flex;
    flex-direction: column;
    flex: 1 1 0;
    max-height: calc(100% - 165px);

    &-title {
      font-size: rem-calc(14px);
      text-transform: uppercase;
      margin-bottom: rem-calc(15px);
    }

    &-search {
      margin: {
        right: rem-calc(-14px);
        bottom: rem-calc(15px);
        left: rem-calc(-14px);
      };
    }

    &-projects {
      margin: {
        right: rem-calc(-14px);
        left: rem-calc(-14px);
      };
      max-height: calc(100% - 93px);
    }
  }

</style>
