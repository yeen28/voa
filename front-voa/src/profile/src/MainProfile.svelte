<script>
	import Sidebar from "../../components/Sidebar.svelte";
	import Header from "../../components/Header.svelte";
	import { ChartPieSolid, GridSolid, MailBoxSolid, UserSolid, ShoppingBagSolid, ArrowRightToBracketOutline, EditOutline, FireSolid, BookSolid, RestoreWindowOutline, LifeSaverSolid } from 'flowbite-svelte-icons';
    import Profile from "./Profile.svelte";

	const topMenus = [
		{menu: 'Profile', name: 'Profile', icon: 'profile', iconTag: UserSolid, badgeText: ''},
		{menu: 'Dashboard', name: 'Dashboard', icon: 'dashboard', iconTag: ChartPieSolid, badgeText: 'New'},
		{menu: 'Kanban', name: 'Kanban', icon: 'kanban', iconTag: GridSolid, badgeText: ''},
		{menu: 'Team', name: 'Team', icon: 'team', iconTag: MailBoxSolid, badgeText: ''},
		{menu: 'Project', name: 'Project', icon: 'project', iconTag: ChartPieSolid, badgeText: ''}
	];

	const bottomMenus = [
		{menu: 'Sign out', name: 'Sign out', icon: 'signOut', iconTag: ArrowRightToBracketOutline, badgeText: '', run: () => location.href = '/logout'},
		{menu: 'Help', name: 'Help', icon: 'help', iconTag: LifeSaverSolid, badgeText: '3'}
	];
</script>

<Header />
<section class="content-wrap">
	<Sidebar topMenus={topMenus} bottomMenus={bottomMenus}/>
	<Profile/>
</section>

<style>
	.content-wrap {
		display: flex;
	}
</style>