<script>
	function create() {
		fetch('/issue', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			// TODO 모델로 만들어서 입력한 값을 백으로 넘겨주기
			body: JSON.stringify({
				issueTypeId: 1,
				title: 'voa issue',
				rank: 1,
				versionNames: ['2305'],
				ownerId: 1,
				reporterId: 1,
				env: 'Windows',
				description: '이슈생성합니다.',
				labelNames: ['라벨'],
				issueLinkType: 1,
				issueLink: 'ISSUE-01'
			})
		})
			.then(response => response.json())
			.then(response => {
				console.log(response);
				closeTemplate();
			})
			.catch(e => console.log(e));
	}

	function closeTemplate() {
		const elCreateIssue = document.getElementById('create-issue');
		const elContents = document.getElementsByClassName('contents')[0];
		const elGnb = document.getElementById('gnb');
		elCreateIssue.classList.add('hide');
		elGnb.classList.remove('hidden');
		elContents.classList.remove('hidden');
	}
</script>

<div id="create-issue" class="hide">
	<div class="issue-header-wrap">
		<span class="issue-header-text">이슈 만들기</span>
	</div>
	<div class="issue-wrap">
		<div class="issue-field-wrap">
			<div class="issue-title-wrap">
				<span class="issue-tiny-text issue-title-align">이슈 유형*</span>
			</div>
			<div class="issue-content-wrap">
				<select id="issue-type-select">
					<option value="1">&#128030;버그</option>
					<option value="2">&#9989;작업</option>
					<option value="3">&#128161;개선사항</option>
					<option value="4">&#128210;스토리</option>
				</select>
				<span class="issue-content-desc">이슈 유형을 선택해 주세요.</span>
			</div>
		</div>
		<div class="line"></div>
		<div class="issue-field-wrap">
			<div class="issue-title-wrap">
				<span class="issue-tiny-text">제목*</span>
			</div>
			<div class="issue-content-wrap">
				<input id="issue-title-input" class="issue-input" type="text"/>
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
				<textarea id="issue-desc-content"></textarea>
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
	<div class="issue-footer-wrap">
		<button class="create-issue-btn button" data-obj="issueManager" on:click={create}>만들기</button>
		<button id="cancel-issue" class="cancel-issue-btn" data-obj="issueManager" on:click={closeTemplate}>취소</button>
	</div>
</div>