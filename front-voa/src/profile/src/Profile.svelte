<script>
	import Sidebar from "../../components/Sidebar.svelte";
	import Header from "../../components/Header.svelte";
	import { ChartPieSolid, GridSolid, MailBoxSolid, UserSolid, ShoppingBagSolid, ArrowRightToBracketOutline, EditOutline, FireSolid, BookSolid, RestoreWindowOutline, LifeSaverSolid } from 'flowbite-svelte-icons';

	const topMenus = [
		{menu: 'Profile', name: 'Profile', icon: 'profile', iconTag: UserSolid},
		{menu: 'Dashboard', name: 'Dashboard', icon: 'dashboard', iconTag: ChartPieSolid},
		{menu: 'Kanban', name: 'Kanban', icon: 'kanban', iconTag: GridSolid},
		{menu: 'Team', name: 'Team', icon: 'team', iconTag: MailBoxSolid},
		{menu: 'Project', name: 'Project', icon: 'project', iconTag: ChartPieSolid}
	];

	const bottomMenus = [
		{menu: 'Logout', name: 'Logout', icon: 'logout', iconTag: ArrowRightToBracketOutline},
		{menu: 'Help', name: 'Help', icon: 'help', iconTag: LifeSaverSolid}
	];
</script>

<div>
	<Sidebar topMenus={topMenus} bottomMenus={bottomMenus}/>
</div>