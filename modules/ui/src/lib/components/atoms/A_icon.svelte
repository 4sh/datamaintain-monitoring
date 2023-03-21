<script lang="ts">
    export let type: string;
    export let size: string;
</script>

<span class="material-symbols-outlined _{size}">{type}</span>

<style lang="scss">
  .material-symbols-outlined {
    font-family: 'Material Icons Two Tone';
    font-variation-settings: 'FILL' 0,
    'wght' 400,
    'GRAD' 0,
    'opsz' 48;

    /* SIZE */

    &._small {
      font-size: 12px;
    }
  }
</style>
