<script>
	function getIssueStatus(id) {
		const elIssueStatusSelect = document.getElementById(id);
		return elIssueStatusSelect.options[elIssueStatusSelect.selectedIndex].value;
	}

	function getIssueTypeId(id) {
		const elIssueTypeSelect = document.getElementById(id);
		return elIssueTypeSelect.options[elIssueTypeSelect.selectedIndex].value
	}

	function getIssueRank(id) {
		const elIssueRankSelect = document.getElementById(id);
		return elIssueRankSelect.options[elIssueRankSelect.selectedIndex].value
	}

	function getIssueOwner(id) {
		const elIssueOwnerSelect = document.getElementById(id);
		return elIssueOwnerSelect.options[elIssueOwnerSelect.selectedIndex].value
	}

	function getIssueReporter(id) {
		const elIssueReporterSelect = document.getElementById(id);
		return elIssueReporterSelect.options[elIssueReporterSelect.selectedIndex].value
	}

	function getIssueTitle(id) {
		const elTitleInput = document.getElementById(id);
		return elTitleInput.value;
	}

	function getIssueVersion(id) {
		const elVersionInput = document.getElementById(id);
		return elVersionInput.value;
	}

	function getIssueLabel(id) {
		const elLabelInput = document.getElementById(id);
		return elLabelInput.value;
	}

	function getIssueRelation(id) {
		const elRelationInput = document.getElementById(id);
		return elRelationInput.value;
	}

	function getIssueEnv(id) {
		const elEnvContent = document.getElementById(id);
		return elEnvContent.value;
	}

	function getIssueDesc(id) {
		const elDescContent = document.getElementById(id);
		return elDescContent.value;
	}

	function updateIssue() {
		const issueId = document.getElementById('edit-issue').getAttribute('data-issue-id');

		//WIP request
		fetch(`/issue/${issueId}`, {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				projectId: '1', //TODO projectId
				issueStatus: 'TO_DO', // issueStatus: getIssueStatus('issue-edit-status-select'),
				issueTypeId: getIssueTypeId('issue-edit-type-select'),
				title: getIssueTitle('issue-edit-title-input'),
				labelNames: getIssueLabel('issue-edit-label-input').split(','),
				versionNames: getIssueVersion('issue-edit-version-input').split(','),
				env: getIssueEnv('issue-edit-env-content'),
				description: getIssueDesc('issue-edit-desc-content'),
				rank: '1', //TODO rank
				ownerId: '1', //TODO ownerId
				reporterId: '1', //TODO reporterId
				issueLink: getIssueRelation('issue-edit-relation-input')
			})
		})
			.then(response => response.json())
			.then(newIssue => {
				console.log(newIssue);
				if (document.getElementById('issue-track-body').classList.contains('hide')) {
					// this.render.rendIssueIntoTable(newIssue);
				} else {
					// this.render.rendIssueIntoBoard(newIssue);
				}

				const elEditIssue = document.getElementById('edit-issue');
				elEditIssue.classList.add('hide');
			})
			.catch(error => {
				const elEditIssue = document.getElementById('edit-issue');
				elEditIssue.classList.add('hide');
				console.log(error);
			});

		closeEditTemplate();
	}

	function closeEditTemplate() {
		const elEditIssue = document.getElementById('edit-issue');
		elEditIssue.classList.add('hide');
	}
</script>

<div id="edit-issue" class="hide">
	<div class="issue-header-wrap">
	</div>
	<div class="issue-wrap">
		<div class="issue-field-wrap">
			<div class="issue-title-wrap">
				<span class="issue-tiny-text issue-title-align">이슈 유형*</span>
			</div>
			<div class="issue-content-wrap">
				<select id="issue-edit-type-select">
					<option value="1">&#128030;버그</option>
					<option value="2">&#9989;작업</option>
					<option value="3">&#128161;개선사항</option>
					<option value="4">&#128210;스토리</option>
				</select>
			</div>
		</div>
		<div class="line"></div>
		<div class="issue-field-wrap">
			<div class="issue-title-wrap">
				<span class="issue-tiny-text">제목*</span>
			</div>
			<div class="issue-content-wrap">
				<input id="issue-edit-title-input" class="issue-input" type="text"/>
			</div>
		</div>
		<div class="issue-field-wrap">
			<div class="issue-title-wrap">
				<span class="issue-tiny-text">우선순위</span>
			</div>
			<div class="issue-content-wrap">
				<select id="issue-edit-rank-select">
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
				<input id="issue-edit-version-input" class="issue-input" type="text" data-type="edit" data-obj="issueManager" data-cmd="showVersions" autocomplete="off"/>
			</div>
		</div>
		<div class="issue-field-wrap">
			<div class="issue-title-wrap">
				<span class="issue-tiny-text issue-title-align">담당자*</span>
			</div>
			<div class="issue-content-wrap">
				<select id="issue-edit-owner-select">
					<option value="auto">자동</option>
				</select>
			</div>
		</div>
		<div class="issue-field-wrap">
			<div class="issue-title-wrap">
				<span class="issue-tiny-text issue-title-align">환경</span>
			</div>
			<div class="issue-content-wrap">
				<textarea id="issue-edit-env-content"></textarea>
			</div>
		</div>
		<div class="issue-field-wrap">
			<div class="issue-title-wrap">
				<span class="issue-tiny-text">설명</span>
			</div>
			<div class="issue-content-wrap">
				<textarea id="issue-edit-desc-content"></textarea>
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
				<input id="issue-edit-label-input" class="issue-input" type="text" data-type="edit" data-obj="issueManager" data-cmd="showLabels" autocomplete="off"/>
			</div>
		</div>
		<div class="issue-field-wrap">
			<div class="issue-title-wrap">
				<span class="issue-tiny-text">연결된 이슈</span>
			</div>
			<div class="issue-content-wrap">
				<select id="issue-edit-relation-select">
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
				<input id="issue-edit-relation-input" class="issue-input" type="text"/>
			</div>
		</div>
	</div>
	<div class="issue-footer-wrap">
		<button class="create-issue-btn button" data-obj="render" on:click={updateIssue}>확인</button>
		<button id="cancel-edit-issue" class="cancel-issue-btn" data-obj="render" on:click={closeEditTemplate}>취소</button>
	</div>
</div>