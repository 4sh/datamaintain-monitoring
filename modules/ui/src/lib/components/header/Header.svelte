<script lang="ts">
import ProjectHierarchy from "../menu/Hierarchy.svelte";
</script>

<div class="header">
	<div class="header-logo">
		<div class="header-logo-img">
			<img src="src/assets/images/svg/datamaintain-logo-picto.svg" alt="Logo Datamaintain Picto">
		</div>
		<div class="header-logo-name">
			<img src="src/assets/images/svg/datamaintain-logo-letters.svg" alt="Logo Datamaintain Letters">
		</div>
	</div>
	<div class="header-hierarchy">
		<ProjectHierarchy></ProjectHierarchy>
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

				img {
					width: 100%;
				}
			}
		}

		&-hierarchy {
			padding: 15px 30px;
		}
	}
</style>
