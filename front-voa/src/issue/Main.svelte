<script>
	import IssueGnb from './IssueGnb.svelte';
	import IssueBoard from './IssueBoard.svelte';
	import IssueTable from './IssueTable.svelte';
	import BottomNav from '../components/BottomNav.svelte';

	let viewType = 'board'; // issue 보여주기 유형 - board | table
</script>

<section>
	<IssueGnb on:viewType={(event) => viewType = event.detail}/>

	{#if viewType === 'board'}
		<IssueBoard/>
	{:else}
		<IssueTable/>
	{/if}
	<BottomNav/>
</section>

<style>
	:global(body) {
		width: initial;
		margin: 30px auto 0 !important;
	}

	section {
		display: grid;
		gap: 15px;
		margin: 0 35px;
	}
</style>