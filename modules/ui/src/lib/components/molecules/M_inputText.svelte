<script lang="ts">
    import A_label from "$lib/components/atoms/A_label.svelte";
    import A_inputText from "$lib/components/atoms/form/A_inputText.svelte";

    export let label;
    export let value;
    export let placeholder;
</script>

<A_label {label}>
    <A_inputText {value} {placeholder}></A_inputText>
</A_label>
