<script>
	import CreateIssueContent from './CreateIssueContent.svelte';

	export let showCreateIssue;

	let createIssue = {
		typeId: 1,
		title: '',
		rank: 1,
		versionNames: [],
		ownerId: 1, // TODO userInfo로 수정
		reporterId: 1, // TODO userInfo로 수정
		env: '' || 'Windows',
		description: '',
		labelNames: [],
		issueLinkType: 0,
		issueLink: 'ISSUE-01'
	}

	function create() {
		fetch('/issue', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(createIssue)
		})
			.then(response => response.json())
			.then(response => {
				showCreateIssue = false;
				location.reload();
			})
			.catch(e => console.error(e));
	}
</script>

{#if showCreateIssue}
	<div id="create-issue">
		<div class="issue-header-wrap">
			<span class="issue-header-text">이슈 만들기</span>
		</div>
		<CreateIssueContent bind:createIssue/>
		<div class="issue-footer-wrap">
			<button class="create-issue-btn button" data-obj="issueManager" on:click={create}>만들기</button>
			<button id="cancel-issue" class="cancel-issue-btn" data-obj="issueManager" on:click={() => showCreateIssue = false}>취소</button>
		</div>
	</div>
{/if}

<style>
	#create-issue {
		z-index: 1;
		left: 10%;
		top: initial;
	}
</style>
