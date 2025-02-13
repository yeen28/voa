<script>
	import {Select, Input} from 'flowbite-svelte';

	export let createIssue;

	let typeOptions = [
		{ value: 1, name: '🐞버그' },
		{ value: 2, name: '✅작업' },
		{ value: 3, name: '💡개선사항' },
		{ value: 4, name: '📋스토리' }
	];
</script>

<div class="issue-wrap">
	<div class="issue-field-wrap">
		<div class="issue-title-wrap">
			<span class="issue-tiny-text issue-title-align">이슈 유형*</span>
		</div>
		<div class="issue-content-wrap">
			<Select id="issue-type-select" items="{typeOptions}" bind:value={createIssue.typeId}/>
			<span class="issue-content-desc">이슈 유형을 선택해 주세요.</span>
		</div>
	</div>
	<div class="line"></div>
	<div class="issue-field-wrap">
		<div class="issue-title-wrap">
			<span class="issue-tiny-text">제목*</span>
		</div>
		<div class="issue-content-wrap">
			<Input id="issue-title-input" class="issue-input" bind:value={createIssue.title}/>
		</div>
	</div>
	<div class="issue-field-wrap">
		<div class="issue-title-wrap">
			<span class="issue-tiny-text">우선순위</span>
		</div>
		<div class="issue-content-wrap">
			<select id="issue-rank-select">
				<option value="major">&#128293;주요</option>
				<option value="critical">&#128163;크리티컬</option>
				<option value="minor">&#10134;마이너</option>
				<option value="trivial">&#11015;사소한</option>
			</select>
		</div>
	</div>
	<div class="issue-field-wrap">
		<div class="issue-title-wrap">
			<span class="issue-tiny-text issue-title-align">버전</span>
		</div>
		<div class="issue-content-wrap">
			<input id="issue-new-version-input" class="issue-input" type="text" data-type="new" data-obj="issueManager" data-cmd="showVersions" autocomplete="off"/>
			<span class="issue-content-desc">키보드 입력을 시작해서 사용 가능한 목록을 가져오거나 아래를 눌러 선택하세요.</span>
		</div>
	</div>
	<div class="issue-field-wrap">
		<div class="issue-title-wrap">
			<span class="issue-tiny-text issue-title-align">담당자*</span>
		</div>
		<div class="issue-content-wrap">
			<select id="issue-owner-select">
				<option value="auto">자동</option>
			</select>
			<span id="assign-me">나에게 할당</span>
		</div>
	</div>
	<div class="issue-field-wrap">
		<div class="issue-title-wrap">
			<span class="issue-tiny-text issue-title-align">환경</span>
		</div>
		<div class="issue-content-wrap">
			<textarea id="issue-env-content"></textarea>
			<span class="issue-content-desc">예를 들면 운영체제, 소프트웨어 플랫폼 또는 하드웨어 상세내역(이슈를 위해 적절하게 포함되어야 함).</span>
		</div>
	</div>
	<div class="issue-field-wrap">
		<div class="issue-title-wrap">
			<span class="issue-tiny-text">설명</span>
		</div>
		<div class="issue-content-wrap">
			<textarea id="issue-desc-content" bind:value={createIssue.description}></textarea>
		</div>
	</div>
	<div class="issue-field-wrap">
		<div class="issue-title-wrap">
			<span class="issue-tiny-text">첨부파일</span>
		</div>
		<div class="issue-content-wrap">
			<input class="issue-input" type="text"/>
		</div>
	</div>
	<div class="issue-field-wrap">
		<div class="issue-title-wrap">
			<span class="issue-tiny-text issue-title-align">라벨</span>
		</div>
		<div class="issue-content-wrap">
			<input id="issue-new-label-input" class="issue-input" type="text" data-type="new" data-obj="issueManager" data-cmd="showLabels" autocomplete="off"/>
			<span class="issue-content-desc">새 라벨을 만들거나 찾으려면 키보드 입력을 시작하세요. 추천 라벨을 보시려면 목록을 여세요.</span>
		</div>
	</div>
	<div class="issue-field-wrap">
		<div class="issue-title-wrap">
			<span class="issue-tiny-text">연결된 이슈</span>
		</div>
		<div class="issue-content-wrap">
			<select id="issue-relation-select">
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
			<input id="issue-relation-input" class="issue-input" type="text"/>
			<span class="issue-content-desc">키보드 입력을 시작해서 사용 가능한 목록을 가져오거나 아래를 눌러 선택하세요.</span>
		</div>
	</div>
</div>

<style>
	:global(#create-issue .issue-input, #create-issue #issue-type-select) {
		width: 500px;
		border: 1px solid #ccc;
	}

	.issue-wrap {
		overflow: auto;
		height: 75vh;
	}
</style>
