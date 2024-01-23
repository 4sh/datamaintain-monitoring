<script lang="ts">
    import A_label from "$lib/components/atoms/A_label.svelte";
    import A_inputText from "$lib/components/atoms/form/A_inputText.svelte";

    export let label;
    export let value;
    export let required;
    export let placeholder;
    export let id: string;
</script>

<A_label {label} inputId>
    <A_inputText bind:value={value} {required} {placeholder} {id}></A_inputText>
</A_label>
