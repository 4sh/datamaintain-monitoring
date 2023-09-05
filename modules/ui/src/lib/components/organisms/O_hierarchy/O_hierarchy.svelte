<script lang="ts">
    import {ProjectService} from "$lib/services/ProjectService";
    import {HierarchySearch} from "$lib/domain/ProjectHierarchy";
    import O_hierarchyProject from "$lib/components/organisms/O_hierarchy/O_hierarchyProject.svelte";
    import M_inputSearch from "$lib/components/molecules/M_inputSearch.svelte";
    import M_error from "$lib/components/molecules/M_error.svelte";
    import {Svroller} from "svrollbar";

    let projectHierarchiesPromise = ProjectService.projectHierarchies();
    let timer;

    function filterHierarchy(event) {
        debounce(event, event => {
            projectHierarchiesPromise = ProjectService.projectHierarchies().then(result => {
                return new HierarchySearch(event.target.value).filter(result);
            })
        });
    }

    function debounce(event, callback) {
        clearTimeout(timer);

        timer = setTimeout(() => {
            callback(event);
        }, 400);
    }
</script>

<div class="hierarchy">
    <div class="hierarchy-title">
        Modules
    </div>
    <div class="hierarchy-search">
        <M_inputSearch on:input="{filterHierarchy}"/>
    </div>

    <div class="hierarchy-projects">
        {#await projectHierarchiesPromise}
            <p>...waiting</p>
        {:then projectHierarchies}

            <Svroller>
                {#each projectHierarchies as project}
                    <O_hierarchyProject {project}/>
                {/each}
            </Svroller>

        {:catch error}
            <M_error>
                Env not found !
            </M_error>
        {/await}
    </div>
</div>

<style lang="scss">
  @import "src/app";

  .hierarchy {
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
