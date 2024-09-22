<script>
	import {createEventDispatcher, onMount} from 'svelte';
	import {Button, Textarea, Select, Input} from 'flowbite-svelte';

	export let issue;
	const dispatch = createEventDispatcher();
	let parts = [];
	let versionName = '';

	let updatedIssue = {
		typeId: 0,
		title: '',
		rank: 0,
		versionNames: [],
		ownerId: 0,
		reporterId: 0,
		env: '',
		description: '',
		labelNames: [],
		issueLinkType: 0,
		issueLink: '',
		issueStatus: '',
	};

	const rankOptions = [
		{ value: "1", name: '🔥주요' },
		{ value: "2", name: '💥크리티컬' },
		{ value: "3", name: '➖마이너' },
		{ value: "4", name: '↘️사소한' }
	];

	const typeOptions = [
		{ value: 1, name: '🐞버그' },
		{ value: 2, name: '✅작업' },
		{ value: 3, name: '💡개선사항' },
		{ value: 4, name: '📋스토리' }
	];

	const linkedIssue = [
		{ value: 'duplicate', name: '다음 이슈와 중복됨'},
		{ value: 'relation', name: '다음 이슈와 연관됨'}
	];

	// Issue data initialization
	onMount(() => {
		if (issue) {
			updatedIssue.typeId = issue.typeId;
			updatedIssue.issueStatus = issue.issueStatus;
			updatedIssue.title = issue.title;
			updatedIssue.rank = issue.rank;
			updatedIssue.versionNames = issue.versionNames;
			updatedIssue.ownerId = 1;
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
	}

	function handleVersionNamesInput(event) {
		versionName = event.target.value;

		if (versionName.includes(',')) {
			parts = parts.concat(versionName.split(',').map(part => part.trim()).filter(part => part));
			versionName = '';
			issue.versionNames = parts;
		}
	}

	function handleLabelNamesInput(event) {
		issue.labelNames = event.target.value.split(',').map(name => name.trim()).filter(name => name);
	}

	$: if (issue && issue.versionNames) {
		parts = [...issue.versionNames];
	}
</script>

{#if issue}
	<div id="edit-issue">
		<div class="issue-wrap">
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text issue-title-align">이슈 유형*</span>
				</div>
				<div class="issue-content-wrap">
					<Select class="mt-2" items={typeOptions} bind:value={issue.typeId} />
				</div>
			</div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text">제목*</span>
				</div>
				<div class="issue-content-wrap">
					<Input id="small-input" size="sm" placeholder="제목을 입력하세요." bind:value={issue.title} class="issue-input" type="text" />
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
					<div>
						{#each parts as part (part)}
							{#if part}
								<span class="part">{part}</span>
							{/if}
						{/each}
					</div>
					<Input
						id="small-input"
						size="sm"
						placeholder="버전을 콤마(,)로 구분하여 입력하세요."
						bind:value={versionName}
						on:input={handleVersionNamesInput}
						class="issue-input"
						type="text"
					/>
				</div>
			</div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text issue-title-align">담당자*</span>
				</div>
				<div class="issue-content-wrap">
					<select bind:value={issue.ownerId}>
						<option value={issue.ownerId}>자동</option>
					</select>
				</div>
			</div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text issue-title-align">환경</span>
				</div>
				<div class="issue-content-wrap">
					<Textarea placeholder="환경을 입력하세요." rows="4" bind:value={issue.env}></Textarea>
				</div>
			</div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text">설명</span>
				</div>
				<div class="issue-content-wrap">
					<Textarea placeholder="설명을 입력하세요." rows="4" bind:value={issue.description}></Textarea>
				</div>
			</div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text">첨부파일</span>
				</div>
				<div class="issue-content-wrap">
					<Input id="small-input" size="sm" class="issue-input" type="file" />
				</div>
			</div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text issue-title-align">라벨</span>
				</div>
				<div class="issue-content-wrap">
					<Input
						id="small-input"
						size="sm"
						placeholder="라벨을 입력하세요."
						value={issue.labelNames.join(', ')}
						on:input={handleLabelNamesInput}
						class="issue-input"
						type="text"
					/>
				</div>
			</div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text">연결된 이슈</span>
				</div>
				<div class="issue-content-wrap">
					<Select items={linkedIssue} bind:value={issue.issueLink}/>
				</div>
			</div>
			<div class="issue-field-wrap">
				<div class="issue-title-wrap">
					<span class="issue-tiny-text issue-title-align">이슈</span>
				</div>
				<div class="issue-content-wrap">
					<Input id="small-input" size="sm" placeholder="None" bind:value={issue.issueLinkType} class="issue-input" type="text" />
				</div>
			</div>
		</div>
		<div class="issue-footer-wrap">
			<Button class="create-issue-btn" on:click={updateIssue}>확인</Button>
			<Button class="cancel-issue-btn" on:click={closeEditTemplate}>취소</Button>
			<Button class="delete-issue-btn" on:click={deleteIssue}>삭제</Button>
		</div>
	</div>
{/if}

<style>
	#edit-issue {
		position: initial;
		border: initial;
	}

	.part {
		display: inline-block;
		padding: 4px 8px;
		margin: 4px;
		border: 1px solid #ccc;
		border-radius: 4px;
	}

	.issue-content-wrap {
		display: flex;
		flex-wrap: wrap;
		gap: 4px;
		border-radius: 4px;
		margin: 2px 0;
	}

	.issue-field-wrap {
		padding: 5px 0;
	}

	#edit-issue .issue-wrap {
		background-color: initial;
	}

	:global(
	#edit-issue select,
	#edit-issue input,
	#edit-issue textarea
	) {
		padding: 8px;
		border: 1px solid #ddd;
		border-radius: 4px;
		background-color: #fff;
		font-size: 0.8rem;
	}

	#edit-issue .issue-footer-wrap {
		display: flex;
		justify-content: flex-end;
		background-color: initial;
		border-bottom: initial;
		border-radius: initial;
	}

	:global(
	.issue-footer-wrap .create-issue-btn,
	.issue-footer-wrap .cancel-issue-btn,
	.issue-footer-wrap .delete-issue-btn
	) {
		padding: 8px 12px;
		border-radius: 4px;
		font-size: 0.9rem;
		color: white;
	}

	:global(.issue-footer-wrap .create-issue-btn) {
		background-color: blue;
	}

	:global(.issue-footer-wrap .cancel-issue-btn) {
		background-color: grey;
	}

	:global(.issue-footer-wrap .delete-issue-btn) {
		background-color: red;
	}
</style>