<script>
	import { BottomNav, BottomNavItem } from 'flowbite-svelte';
	import {HomeSolid, LockSolid, UserCircleSolid} from 'flowbite-svelte-icons';

	const movePage = url => {
		location.href = url;
	}
</script>

<BottomNav position="absolute" classInner="grid-cols-3" style="position:sticky">
	<BottomNavItem btnName="Home" on:click={() => movePage('/main')}>
		<HomeSolid class="w-6 h-6 mb-1 text-gray-500 dark:text-gray-400 group-hover:text-primary-600 dark:group-hover:text-primary-500" />
	</BottomNavItem>
	<BottomNavItem btnName="Jake" on:click={() => movePage('/profile')}>
		<UserCircleSolid class="w-6 h-6 mb-1 text-gray-500 dark:text-gray-400 group-hover:text-primary-600 dark:group-hover:text-primary-500" />
	</BottomNavItem>
	<BottomNavItem btnName="Logout" on:click={() => movePage('/logout')}>
		<LockSolid class="w-6 h-6 mb-1 text-gray-500 dark:text-gray-400 group-hover:text-primary-600 dark:group-hover:text-primary-500" />
	</BottomNavItem>
</BottomNav>

<style>
</style>