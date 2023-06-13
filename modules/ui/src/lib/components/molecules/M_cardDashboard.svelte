<script lang="ts">
    import A_icon from "$lib/components/atoms/A_icon.svelte";
    import A_indicator from "$lib/components/atoms/A_indicator.svelte";
    import M_progressBar from "$lib/components/molecules/M_progressBar.svelte";

    export let cardStatus;
    export let projectLabel;
    export let envLabel;
    export let cardTitle: string = '';
    export let cardModule: string = '';
</script>

<div class="mCard _{cardStatus}">
    <div class="mCard-header">
        <div class="flexAuto">
            <div class="mCard-icon">
                <A_icon type="{cardStatus === 'check' ? 'task_alt' : 'autorenew'}" size="extraLarge"></A_icon>
            </div>
            <div>
                <div class="mCard-title">
                    {cardTitle}
                </div>
                <div class="mCard-module">
                    {cardModule}
                </div>
            </div>
        </div>
        <div class="flexShrink">
            <div class="mCard-indicators">
                {#if projectLabel}
                    <div class="mCard-indicators-item">
                        <A_indicator label="{projectLabel}"></A_indicator>
                    </div>
                {/if}
                {#if envLabel}
                    <div class="mCard-indicators-item">
                        <A_indicator label="{envLabel}"></A_indicator>
                    </div>
                {/if}
            </div>
            <div class="mCard-favorite">
                <A_icon type="star_border" size="semiLight"></A_icon>
            </div>
        </div>
    </div>
    <div class="mCard-content">
        {#if $$slots.startExeDate || $$slots.startExeTime}
            <span>Début d’execution :</span>
            <slot name="startExeDate"></slot>
            {#if $$slots.startExeDate && $$slots.startExeTime}
                à
            {/if}
            <slot name="startExeTime"></slot>
            <br>
        {/if}
        {#if $$slots.timeExe}
            <span>Temps d’execution :</span>
            <slot name="timeExe"></slot>
            <br>
        {/if}
        {#if $$slots.nbScript}
            <span>Nombre de scripts :</span>
            <slot name="nbScript"></slot>
            <br>
        {/if}
        {#if $$slots.nbKo}
            <span>KO :</span>
            <slot name="nbKo"></slot>
            <br>
        {/if}
        {#if $$slots.nbOk}
            <span>OK :</span>
            <slot name="nbOk"></slot>
        {/if}
    </div>
    <div class="mCard-progressBar">
        <M_progressBar></M_progressBar>
    </div>
    <div class="mCard-link">
        Accéder au détails
    </div>
</div>

<style lang="scss">
  @import "src/app";

  .mCard {
    padding: rem-calc(30px 30px 25px 20px);
    background-color: $app-primary_900;
    border-radius: rem-calc(4px);
    flex: 1 1 0;
    display: flex;
    flex-direction: column;

    &._check {
      .mCard-icon, .mCard-title {
        color: $app-success_900;
      }
    }

    &._error {
      .mCard-icon, .mCard-title {
        color: $app-error_900;
      }
    }

    &-header {
      display: flex;

      .flexAuto {
        display: flex;
        align-items: center;
        flex: 1 1 0;
      }

      .flexShrink {
        display: flex;
        align-items: center;
        height: rem-calc(30px);
      }
    }

    &-icon {
      margin-right: rem-calc(12px);
      height: rem-calc(50px);
      color: $app-primary_700;
    }

    &-title {
      font-size: rem-calc(20px);
      font-weight: 600;
      color: $app-primary_700;
    }

    &-module {
      font-size: rem-calc(14px);
    }

    &-indicators {
      display: flex;

      &-item {
        margin-left: rem-calc(10px);
      }
    }

    &-favorite {
      margin-left: rem-calc(15px);
      display: flex;
      align-items: center;

      &:hover {
        cursor: pointer;
      }
    }

    &-content {
      padding: rem-calc(20px 6px 10px);
      font-size: rem-calc(14px);
      font-weight: 300;

      span {
        font-weight: 500;
      }
    }

    &-progressBar {
      margin: rem-calc(10px) 0 rem-calc(15px);
    }

    &-link {
      font-size: rem-calc(14px);
      text-align: right;

      &:hover {
        cursor: pointer;
      }
    }
  }

</style>
