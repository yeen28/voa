<script>
	import Gnb from '../component/Gnb.svelte';
	import CreateIssueTemplate from './CreateIssueTemplate.svelte';
	import EditIssueTemplate from './EditIssueTemplate.svelte';
	import IssueCard from './IssueCard.svelte';
	import { onMount } from 'svelte';

	const statusEnum = {todo: 'TO_DO', progress: 'PROGRESS', resolve: 'RESOLVE'};
	let issues = [];
	let toDoIssues = [];
	let progressIssues = [];
	let resolveIssues = [];
	let selectedIssue = null;

	onMount(() => {
		fetch('/issues?ownerId=1')
			.then(response => response.json())
			.then(data => {
				issues = data;
				toDoIssues = data.filter(d => d.issueStatus === statusEnum.todo);
				progressIssues = data.filter(d => d.issueStatus === statusEnum.progress);
				resolveIssues = data.filter(d => d.issueStatus === statusEnum.resolve);
			})
			.catch(error => console.log(error));
	});

	function handleIssueClick(id) {
		fetch(`/issue/${id}`)
			.then(response => response.json())
			.then(data => {
				selectedIssue = data;
			})
			.catch(error => console.log(error));
	}

	function updateIssue(event) {
		const updatedIssue = event.detail;
		issues = issues.map(issue => (issue.id === updatedIssue.id ? updatedIssue : issue));
		selectedIssue = null;
	}

	function deleteIssue(event) {
		const deletedIssueId = event.detail;
		issues = issues.filter(issue => issue.id !== deletedIssueId);
		selectedIssue = null;
	}

	function closeEditModal() {
		selectedIssue = null;
	}
</script>

<Gnb />
<section>
	<div class="contents box track">
		<div id="issue-track-body">
			<div class="issue-todo-wrap">
				<div class="issue-sub-track-title">
					<span class="text">Todo</span>
				</div>
				<div class="issue-item-wrap issue-todo-item-wrap">
					{#each toDoIssues as issue (issue.id)}
						<IssueCard {issue} on:click={handleIssueClick} onClick={handleIssueClick} />
					{/each}
				</div>
			</div>
			<div class="issue-progress-wrap">
				<div class="issue-sub-track-title">
					<span class="text">Progress</span>
				</div>
				<div class="issue-item-wrap issue-progress-item-wrap">
					{#each progressIssues as issue (issue.id)}
						<IssueCard {issue} on:click={handleIssueClick} onClick={handleIssueClick} />
					{/each}
				</div>
			</div>
			<div class="issue-resolve-wrap">
				<div class="issue-sub-track-title">
					<span class="text">Resolve</span>
				</div>
				<div class="issue-item-wrap issue-resolve-item-wrap">
					{#each resolveIssues as issue (issue.id)}
						<IssueCard {issue} on:click={handleIssueClick} onClick={handleIssueClick} />
					{/each}
				</div>
			</div>
		</div>

		<table id="issue-table" class="hide">
			<thead>
			<tr>
				<th class="header">유형</th>
				<th class="header">키</th>
				<th class="header">담당자</th>
				<th class="header">보고자</th>
				<th class="header">우선</th>
				<th class="header">상태</th>
				<th class="header">버전</th>
				<th class="header">변경일</th>
				<th class="header">해결책</th>
				<th class="header">요약</th>
				<th class="header">생성일</th>
			</tr>
			</thead>
			<tbody id="issue-table-tbody"></tbody>
		</table>
	</div>

	<!-- 이슈 만들기 템플릿  -->
	<CreateIssueTemplate />

	<!-- 이슈 편집 템플릿  -->
	{#if selectedIssue}
		<EditIssueTemplate issue={selectedIssue} on:close={closeEditModal} on:update={updateIssue} on:delete={deleteIssue} />
	{/if}
</section>

<style>
</style>
