<script>
	import IssueCard from './IssueCard.svelte';
	import EditIssueTemplate from './EditIssueTemplate.svelte';
	import {onMount} from 'svelte';

	const statusEnum = { todo: 'TO_DO', progress: 'IN_PROGRESS', resolve: 'DONE' };
	let issues = [];
	let toDoIssues = [];
	let progressIssues = [];
	let resolveIssues = [];
	let selectedIssue = null;
	let draggedIssueId = null;

	onMount(() => {
		fetch('/issues')
			.then(response => response.json())
			.then(data => {
				issues = data;
				toDoIssues = data.filter(d => d.issueStatus === statusEnum.todo);
				progressIssues = data.filter(d => d.issueStatus === statusEnum.progress);
				resolveIssues = data.filter(d => d.issueStatus === statusEnum.resolve);
			})
			.catch(error => console.log(error));
	});

	function toggleIssueDetailVisibility(id) {
		const elIssueDetail = document.getElementById('issue-track-detail');
		const isShow = id !== elIssueDetail?.getAttribute('issue-id');

		elIssueDetail?.classList.toggle('hide', !isShow);

		if (!isShow) {
			elIssueDetail.removeAttribute('issue-id');
			selectedIssue = null;
		}

		return isShow;
	}

	function handleIssueClick(id) {
		if (toggleIssueDetailVisibility(id)) {
			fetchIssueDetail(id);
		}
	}

	function fetchIssueDetail(id) {
		fetch(`/issue/${id}`)
				.then(response => response.json())
				.then(data => {
					if (data) {
						const elIssueDetail = document.getElementById('issue-track-detail');
						elIssueDetail.setAttribute('issue-id', data?.id);
						selectedIssue = data;
					}
				})
				.catch(error => console.log(error));
	}

	function handleDragStart(issueId, event) {
		draggedIssueId = issueId;
		event.dataTransfer.setData('text/plain', issueId);
	}

	function handleDrop(status, event) {
		const issueId = draggedIssueId;
		draggedIssueId = null;

		fetch(`/issue/${issueId}/status/${status}`, {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json'
			}
		})
				.then(response => response.json())
				.then(updatedIssue => {
					issues = issues.map(issue => (issue.id === updatedIssue.id ? updatedIssue : issue));
					updateIssueLists();
				})
				.catch(error => console.log(error));
	}

	function allowDrop(event) {
		event.preventDefault();
	}

	function updateIssueLists() {
		toDoIssues = issues.filter(d => d.issueStatus === statusEnum.todo);
		progressIssues = issues.filter(d => d.issueStatus === statusEnum.progress);
		resolveIssues = issues.filter(d => d.issueStatus === statusEnum.resolve);
	}

	function updateIssue(event) {
		const updatedIssue = event.detail;
		issues = issues.map(issue => (issue.id === updatedIssue.id ? updatedIssue : issue));
		updateIssueLists();
		selectedIssue = null;
	}

	function deleteIssue(event) {
		const deletedIssueId = event.detail;
		issues = issues.filter(issue => issue.id !== deletedIssueId);
		updateIssueLists();
		selectedIssue = null;
	}

	function closeEditModal() {
		selectedIssue = null;
		const elIssueDetail = document.getElementById('issue-track-detail');
		elIssueDetail?.classList.add('hide');
	}
</script>

{#if issues?.length !== 0}
	<div class="contents box track">
		<div id="issue-track-body-wrap">
			<div id="issue-track-body">
				<div class="issue-todo-wrap" on:drop={() => handleDrop(statusEnum.todo)} on:dragover={allowDrop}>
					<div class="issue-sub-track-title">
						<span class="text">Todo</span>
					</div>
					<div class="issue-item-wrap issue-todo-item-wrap">
						{#each toDoIssues as issue (issue.id)}
							<IssueCard {issue} onClick={handleIssueClick} onDragStart={handleDragStart} onDragEnd={allowDrop} on:dragstart={handleDragStart} on:dragend={allowDrop} />
						{/each}
					</div>
				</div>
				<div class="issue-progress-wrap" on:drop={() => handleDrop(statusEnum.progress)} on:dragover={allowDrop}>
					<div class="issue-sub-track-title">
						<span class="text">Progress</span>
					</div>
					<div class="issue-item-wrap issue-progress-item-wrap">
						{#each progressIssues as issue (issue.id)}
							<IssueCard {issue} onClick={handleIssueClick} onDragStart={handleDragStart} onDragEnd={allowDrop} on:dragstart={handleDragStart} on:dragend={allowDrop} />
						{/each}
					</div>
				</div>
				<div class="issue-resolve-wrap" on:drop={() => handleDrop(statusEnum.resolve)} on:dragover={allowDrop}>
					<div class="issue-sub-track-title">
						<span class="text">Resolve</span>
					</div>
					<div class="issue-item-wrap issue-resolve-item-wrap">
						{#each resolveIssues as issue (issue.id)}
							<IssueCard {issue} onClick={handleIssueClick} onDragStart={handleDragStart} onDragEnd={allowDrop} on:dragstart={handleDragStart} on:dragend={allowDrop} />
						{/each}
					</div>
				</div>
			</div>
		</div>

		<div id="issue-track-detail" class="hide">
			<div id="issue-track-detail-contents">
				<EditIssueTemplate issue={selectedIssue} on:close={closeEditModal} on:update={updateIssue} on:delete={deleteIssue} />
			</div>
		</div>
	</div>
{/if}

<style>
	.issue-item-wrap {
		min-height: 200px;
		border: 2px dashed #ccc;
		padding: 10px;
	}

	#issue-track-body {
		display: grid;
		grid-template-columns: 1fr 1fr 1fr;
		height: 100%;
		text-align: center;
	}

	.contents.box.track {
		display: table;
		table-layout: fixed;
		width: 100%;
	}

	#issue-track-body-wrap,
	#issue-track-detail {
		display: table-cell;
		height: inherit;
		vertical-align: top;
	}

	#issue-track-detail {
		width: 450px;
		font-size: 0.8rem;
		padding-top: 35px;
	}

	#issue-track-detail-contents {
		display: flex;
		flex-direction: column;
		gap: 1rem;
		padding: 20px 35px;
		background-color: #f8f9fa;
		border-radius: 8px;
		font-family: Arial, sans-serif;
		box-shadow:
				rgba(15, 15, 15, 0.04) 0px 0px 0px 1px,
				rgba(15, 15, 15, 0.03) 0px 3px 6px,
				rgba(15, 15, 15, 0.06) 0px 9px 24px;
	}

	.issue-sub-track-title > span {
		background-color: #D3D1CB;
	}
</style>