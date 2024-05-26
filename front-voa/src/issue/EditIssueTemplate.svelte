<script>
	import {createEventDispatcher, onMount} from 'svelte';
	export let issue = null;
	const dispatch = createEventDispatcher();

	let updatedIssue = {
		issueTypeId: 0,
		title: '',
		rank: '',
		versionNames: '',
		ownerId: 0,
		reporterId: 0,
		env: '',
		description: '',
		labelNames: '',
		issueLinkType: 0,
		issueLink: '',
		issueStatus: '',
	};

	// Issue data initialization
	onMount(() => {
		if (issue) {
			updatedIssue.issueTypeId = issue.id;
			updatedIssue.issueStatus = issue.issueStatus;
			updatedIssue.title = issue.title;
			updatedIssue.rank = issue.rank;
			updatedIssue.versionNames = issue.versionNames;
			updatedIssue.ownerId = '1';
			updatedIssue.env = issue.env;
			updatedIssue.description = issue.description;
			updatedIssue.labelNames = issue.labelNames;
			updatedIssue.issueLinkType = issue.issueLinkType;
			updatedIssue.issueLink = issue.issueLink;
			updatedIssue.reporterId = '1';
		}
	});

	function updateIssue() {
		fetch(`/issue/${issue.id}`, {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(updatedIssue)
		})
			.then(response => response.json())
			.then(newIssue => {
				dispatch('update', newIssue);
				closeEditTemplate();
			})
			.catch(error => {
				console.log(error);
				closeEditTemplate();
			});
	}

	function deleteIssue() {
		fetch(`/issue/${issue.id}`, {
			method: 'DELETE'
		})
			.then(response => response.json())
			.then(() => {
				dispatch('delete', issue.id);
				closeEditTemplate();
			})
			.catch(error => {
				console.log(error);
				closeEditTemplate();
			});
	}

	function closeEditTemplate() {
		dispatch('close');
		window.location.reload();
	}
</script>

{#if issue}
	<div id="edit-issue">
		<div class="issue-header-wrap"></div>
		<div class="issue-wrap">
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text issue-title-align">이슈 유형*</span>
				</div>
				<div class="issue-content-wrap">
					<select bind:value={updatedIssue.issueType}>
						<option value="1">🐞버그</option>
						<option value="2">✅작업</option>
						<option value="3">💡개선사항</option>
						<option value="4">📋스토리</option>
					</select>
				</div>
			</div>
			<div class="line"></div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text">제목*</span>
				</div>
				<div class="issue-content-wrap">
					<input bind:value={updatedIssue.title} class="issue-input" type="text" />
				</div>
			</div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text">우선순위</span>
				</div>
				<div class="issue-content-wrap">
					<select bind:value={updatedIssue.rank}>
						<option value="major">🔥주요</option>
						<option value="critical">💥크리티컬</option>
						<option value="minor">➖마이너</option>
						<option value="trivial">↘️사소한</option>
					</select>
				</div>
			</div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text issue-title-align">버전</span>
				</div>
				<div class="issue-content-wrap">
					<input bind:value={updatedIssue.versionNames} class="issue-input" type="text" />
				</div>
			</div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text issue-title-align">담당자*</span>
				</div>
				<div class="issue-content-wrap">
					<select bind:value={updatedIssue.ownerId}>
						<option value="auto">자동</option>
					</select>
				</div>
			</div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text issue-title-align">환경</span>
				</div>
				<div class="issue-content-wrap">
					<textarea bind:value={updatedIssue.env}></textarea>
				</div>
			</div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text">설명</span>
				</div>
				<div class="issue-content-wrap">
					<textarea bind:value={updatedIssue.description}></textarea>
				</div>
			</div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text">첨부파일</span>
				</div>
				<div class="issue-content-wrap">
					<input class="issue-input" type="text" />
				</div>
			</div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text issue-title-align">라벨</span>
				</div>
				<div class="issue-content-wrap">
					<input bind:value={updatedIssue.labelNames} class="issue-input" type="text" />
				</div>
			</div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text">연결된 이슈</span>
				</div>
				<div class="issue-content-wrap">
					<select bind:value={updatedIssue.issueLink}>
						<option value="duplicate">다음 이슈와 중복됨</option>
						<option value="relation">다음 이슈와 연관됨</option>
					</select>
				</div>
			</div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text issue-title-align">이슈</span>
				</div>
				<div class="issue-content-wrap">
					<input bind:value={updatedIssue.issueLinkType} class="issue-input" type="text" />
				</div>
			</div>
		</div>
		<div class="issue-footer-wrap">
			<button class="create-issue-btn button" on:click={updateIssue}>확인</button>
			<button class="cancel-issue-btn" on:click={closeEditTemplate}>취소</button>
			<button class="delete-issue-btn" on:click={deleteIssue}>삭제</button>
		</div>
	</div>
{/if}

<style>
</style>