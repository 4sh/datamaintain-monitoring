<script lang="ts">
import CurrentUserSubHeader from "./CurrentUserSubHeader.svelte";
import ProjectHierarchy from "../menu/Hierarchy.svelte";
</script>

<div class="header">
	<div class="header-logo">
		<div class="header-logo-img">

		</div>
		<div class="header-logo-name">

		</div>
	</div>
	<CurrentUserSubHeader></CurrentUserSubHeader>
	<ProjectHierarchy></ProjectHierarchy>
</div>

<style lang="scss">
	@import "src/app";

	.header {

	}
</style>
