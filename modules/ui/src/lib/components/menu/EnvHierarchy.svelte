<script lang="ts">
    export let projectRef;
    export let env;
</script>

<div class="env">
    <div class="env-name">
        <a href="/projects/{projectRef}/envs/{env.id}">{env.name}</a>
    </div>

    <div class="env-modules">
        {#each env.modules as module}
            <div>
                <a href="/projects/{projectRef}/modules/{module.id}?env={env.id}">{module.name}</a>
            </div>
        {/each}
    </div>
</div>

<style>
    .env-name {
        font-style: italic;
    }

    .env-modules {
        padding-left: 10px;
    }
</style>
