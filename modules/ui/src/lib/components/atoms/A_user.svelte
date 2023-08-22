<script>
    export let src;
</script>

<div class="aUSer">
    <img src="{src}" alt="Photo de l'utilisateur">
</div>

<style lang="scss">
    @import "src/app";

    .aUSer {
      width: rem-calc(40px);
      height: rem-calc(40px);
      border-radius: rem-calc(20px);
      overflow: hidden;

      img {
        height: 100%;
        width: 100%;
        object-fit: cover;
      }
    }
</style>
