<script>
	import { Sidebar, SidebarGroup, SidebarItem, SidebarWrapper } from 'flowbite-svelte';

	export let topMenus;
	export let bottomMenus;

	let spanClass = 'flex-1 ms-3 whitespace-nowrap';
</script>

<Sidebar style="margin-top: 48px">
	<SidebarWrapper>
		<SidebarGroup>
			{#each topMenus as menu}
				<SidebarItem label={menu.name} {spanClass} href='#' on:click={menu.run}>
					<svelte:fragment slot="icon">
						<svelte:component this={menu.iconTag} class="w-6 h-6 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white" />
					</svelte:fragment>
					<svelte:fragment slot="subtext">
						<span class="inline-flex justify-center items-center px-2 ms-3 text-sm font-medium text-gray-800 bg-gray-200 rounded-full dark:bg-gray-700 dark:text-gray-300">{menu.badgeText}</span>
					</svelte:fragment>
				</SidebarItem>
			{/each}
		</SidebarGroup>
		<SidebarGroup border>
			{#each bottomMenus as menu}
				<SidebarItem label={menu.name} {spanClass} href='#' on:click={menu.run}>
					<svelte:fragment slot="icon">
						<svelte:component this={menu.iconTag} class="w-6 h-6 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white" />
					</svelte:fragment>
					<svelte:fragment slot="subtext">
						<span class="inline-flex justify-center items-center p-3 ms-3 w-3 h-3 text-sm font-medium text-primary-600 bg-primary-200 rounded-full dark:bg-primary-900 dark:text-primary-200"> {menu.badgeText} </span>
					</svelte:fragment>
				</SidebarItem>
			{/each}
		</SidebarGroup>
	</SidebarWrapper>
</Sidebar>