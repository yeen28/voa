<script>
	import {createEventDispatcher, onMount} from 'svelte';
	import {Button, Textarea, Select, Input} from 'flowbite-svelte';
	export let issue = null;
	const dispatch = createEventDispatcher();

	let updatedIssue = {
		typeId: 0,
		title: '',
		rank: 0,
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
	let rankOptions = [
		{ value: 1, name: '🔥주요' },
		{ value: 2, name: '💥크리티컬' },
		{ value: 3, name: '➖마이너' },
		{ value: 4, name: '↘️사소한' }
	];
	let typeOptions = [
		{ value: 1, name: '🐞버그' },
		{ value: 2, name: '✅작업' },
		{ value: 3, name: '💡개선사항' },
		{ value: 4, name: '📋스토리' }
	];

	// Issue data initialization
	onMount(() => {
		if (issue) {
			updatedIssue.typeId = issue.typeId;
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
		updatedIssue.rank = issue.rank;
		fetch(`/issue/${issue.id}`, {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(issue)
		})
			.then(response => response.json())
			.then(newIssue => {
				dispatch('update', newIssue);
				closeEditTemplate();
			})
			.catch(error => {
				console.log(error);
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
					<Select class="mt-2" items={typeOptions} bind:value={updatedIssue.typeId} />
				</div>
			</div>
			<div class="line"></div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text">제목*</span>
				</div>
				<div class="issue-content-wrap">
					<Input id="small-input" size="sm" placeholder="None" bind:value={updatedIssue.title} class="issue-input" type="text" />
				</div>
			</div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text">우선순위</span>
				</div>
				<div class="issue-content-wrap">
					<Select class="mt-2" items={rankOptions} bind:value={issue.rank} />
				</div>
			</div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text issue-title-align">버전</span>
				</div>
				<div class="issue-content-wrap">
					<Input id="small-input" size="sm" placeholder="None" bind:value={updatedIssue.versionNames} class="issue-input" type="text" />
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
					<Textarea placeholder="None" rows="4" bind:value={updatedIssue.env}></Textarea>
				</div>
			</div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text">설명</span>
				</div>
				<div class="issue-content-wrap">
					<Textarea placeholder="None" rows="4" bind:value={updatedIssue.description}></Textarea>
				</div>
			</div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text">첨부파일</span>
				</div>
				<div class="issue-content-wrap">
					<Input id="small-input" size="sm" placeholder="None" class="issue-input" type="text" />
				</div>
			</div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text issue-title-align">라벨</span>
				</div>
				<div class="issue-content-wrap">
					<Input id="small-input" size="sm" placeholder="None" bind:value={updatedIssue.labelNames} class="issue-input" type="text" />
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
					<Input id="small-input" size="sm" placeholder="None" bind:value={updatedIssue.issueLinkType} class="issue-input" type="text" />
				</div>
			</div>
		</div>
		<div class="issue-footer-wrap">
			<Button color="blue" class="create-issue-btn button" on:click={updateIssue}>확인</Button>
			<Button color="alternative" class="cancel-issue-btn" on:click={closeEditTemplate}>취소</Button>
			<Button color="red" class="delete-issue-btn" on:click={deleteIssue}>삭제</Button>
		</div>
	</div>
{/if}

<style>
	#edit-issue {
		position: initial;
	}
</style>