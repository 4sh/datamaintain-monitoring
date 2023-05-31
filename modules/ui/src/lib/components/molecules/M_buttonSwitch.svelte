<script lang="ts">
    import A_label from "$lib/components/atoms/A_label.svelte";
    import A_buttonSwitch from "$lib/components/atoms/A_buttonSwitch.svelte";

    export let checked;
    export let label;
    export let alignment;
    export let flex;
</script>

<A_label {label} {alignment} {flex}>
    <A_buttonSwitch bind:checked={checked}></A_buttonSwitch>
</A_label>
