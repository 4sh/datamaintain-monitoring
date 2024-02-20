<script lang="ts">
    export let label: string;
    export let type: "button" | "submit" | "reset" = 'button';
    // eslint-disable-next-line @typescript-eslint/no-empty-function
    export let onClickAction: () => void = () => {  }
</script>

<button {type} on:click={onClickAction}>{label}</button>
