<script>
    import {Svroller} from "svrollbar";

    export let codeLog = '';
    const codeLogArray = codeLog.split('\n');
    let numberItemLength = ('' + codeLogArray.length).length;
    const maxNumberItemLength = 6;
    const numberItemWidthClass = '_number-item-width-' + (numberItemLength <= maxNumberItemLength ? numberItemLength : maxNumberItemLength);
</script>

<div class="mCodeBlock">
    <Svroller>
        <div class="mCodeBlock-section {numberItemWidthClass}">
            <div class="mCodeBlock-number">
                {#each codeLogArray as codeLogLine, indexLine}
                    <div class="mCodeBlock-number-item">
                        {indexLine + 1}
                    </div>
                {/each}
            </div>
            <div class="mCodeBlock-line">
                <Svroller>
                    {#each codeLogArray as codeLogLine, indexLine}
                        <div class="mCodeBlock-line-item">
                            {codeLogLine}
                        </div>
                    {/each}
                </Svroller>
            </div>
        </div>
    </Svroller>
</div>


<style lang="scss">
  @import "src/app";

  .mCodeBlock {
    border-radius: rem-calc(4px);
    height: 100%;
    border: rem-calc(1px) solid rgba($app-secondary_700, .5);
    font-size: rem-calc(12px);
    line-height: rem-calc(25px);
    position: relative;

    &-section {
      display: flex;
      min-height: 100%;
      counter-reset: section;
      white-space: nowrap;
      white-space-collapse: preserve-breaks;
      font-family: ui-monospace, SFMono-Regular, "SF Mono", Menlo, Consolas, "Liberation Mono", monospace !important;

      &._number-item-width {
        &-1 {
          .mCodeBlock {
            &-number-item {
              width: rem-calc(8px);
            }
            &-line {
              width: calc(100% - 38px);
            }
          }
        }
        &-2 {
          .mCodeBlock {
            &-number-item {
              width: rem-calc(14px);
            }
            &-line {
              width: calc(100% - 44px);
            }
          }
        }
        &-3 {
          .mCodeBlock {
            &-number-item {
              width: rem-calc(22px);
            }
            &-line {
              width: calc(100% - 52px);
            }
          }
        }
        &-4 {
          .mCodeBlock {
            &-number-item {
              width: rem-calc(30px);
            }
            &-line {
              width: calc(100% - 60px);
            }
          }
        }
        &-5 {
          .mCodeBlock {
            &-number-item {
              width: rem-calc(38px);
            }
            &-line {
              width: calc(100% - 68px);
            }
          }
        }
        &-6 {
          .mCodeBlock {
            &-number-item {
              width: rem-calc(46px);
            }
            &-line {
              width: calc(100% - 76px);
            }
          }
        }
      }
    }

    &-number {
      padding: rem-calc(15px);
      background: rgba(var(--secondary-color-rgb) / .1);

      &-item {
        color: $app-secondary_700;
        width: rem-calc(30px);
        text-align: right;
      }
    }

    &-line {
      padding: rem-calc(15px) 0;
      background: $app-primary_900;

      &-item {
        padding: 0 rem-calc(20px);

        &:hover {
          background-color: rgba(var(--secondary-color-rgb)/0.4);
          cursor: pointer;
        }
      }
    }
  }

</style>
