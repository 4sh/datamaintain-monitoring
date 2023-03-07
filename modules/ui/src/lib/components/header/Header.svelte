<script lang="ts">
import CurrentUserSubHeader from "./CurrentUserSubHeader.svelte";
</script>

<header class="app-header">
	<CurrentUserSubHeader></CurrentUserSubHeader>
</header>

<style lang="scss">
	@import "src/app";

	.app {
		&-header {
			background: $bg-color;
		}
	}
</style>
