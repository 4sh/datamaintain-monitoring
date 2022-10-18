<script lang="ts">
    export let env;
</script>

<div class="env">
    <div class="env-name">{env.name}</div>

    <div class="env-modules">
        {#each env.modules as module}
            <div>{module.name}</div>
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
