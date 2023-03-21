<script lang="ts">
import O_projectHierarchy from "$lib/components/organisms/O_hierarchy/O_hierarchy.svelte";
import {currentTheme} from "$lib/stores/theme";
import M_logoText from "$lib/components/molecules/M_logoText.svelte";

function toggleTheme() {
	if($currentTheme === "light") {
		currentTheme.set("dark");
	} else {
		currentTheme.set("light");
	}
}
</script>

<div class="header">
	<div class="header-logo">
		<div class="header-logo-img">
			<img src="./src/assets/images/svg/datamaintain-logo.svg" alt="Logo Datamaintain">
		</div>
		<div class="header-logo-name">
			<M_logoText />
		</div>
	</div>

	<button type="submit" on:click={toggleTheme}>Switch theme</button>

	<div class="header-hierarchy">
		<O_projectHierarchy></O_projectHierarchy>
	</div>
</div>

<style lang="scss">
	@import "src/app";

	.header {
		height: 100%;

		&-logo {
			display: flex;
			flex-direction: row;
			align-items: center;
			padding: 30px;

			&-img {
				background-color: $bg-logo;
				height: 56px;
				width: 56px;
				border-radius: 8px;
				display: flex;
				justify-content: center;
				align-items: center;
			}

			&-name {
				max-width: calc(100% - 80px);
				margin-left: 24px;
			}
		}

		&-hierarchy {
			padding: 15px 30px;
		}
	}
</style>
