<script>
    import A_label from "$lib/components/atoms/A_label.svelte";
    import A_user from "$lib/components/atoms/A_user.svelte";

    export let name;
    export let src;
</script>

<A_label label="{name}" flex="shrink">
    <A_user src="{src}"></A_user>
</A_label>
