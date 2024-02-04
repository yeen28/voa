<script>
	import Gnb from "../component/Gnb.svelte";
	import CreateIssueTemplate from "../issue/CreateIssueTemplate.svelte";

	function getIssue() {
		fetch('/issues?ownerId=1')
			.then(response => response.json())
			.then(issues => {
				issues.forEach(issue => {
					render(issue);
				});
			});
	}

	function render(issue) {
		let elIssueCard = document.createElement('div');
		elIssueCard.classList.add('issue-card')
		elIssueCard.setAttribute('data-id', issue.id);
		elIssueCard.setAttribute('data-obj', 'render');
		elIssueCard.setAttribute('data-cmd', 'clickIssueEvent');
		elIssueCard.innerHTML = (document.getElementById('template-new-issue')).value;

		elIssueCard.getElementsByClassName('issue-card-title-text')[0].textContent = issue.title;
		elIssueCard.getElementsByClassName('issue-card-type')[0].textContent = issue.issueType;
		elIssueCard.getElementsByClassName('issue-card-user')[0].textContent = issue.ownerName;
		elIssueCard.getElementsByClassName('issue-card-version')[0].textContent = issue.versionNames;
		elIssueCard.getElementsByClassName('issue-card-label')[0].textContent = issue.labelNames;

		document.getElementsByClassName('issue-todo-item-wrap')[0].appendChild(elIssueCard)
	}

	getIssue();
</script>

<Gnb/>
<section>
	<div class="contents box track">
		<div id="issue-track-body">
			<div class="issue-todo-wrap">
				<div class="issue-sub-track-title">
					<span class="text">Todo</span>
				</div>
				<div class="issue-item-wrap issue-todo-item-wrap"></div>
			</div>
			<div class="issue-progress-wrap">
				<div class="issue-sub-track-title">
					<span class="text">Progress</span>
				</div>
				<div class="issue-item-wrap issue-progress-item-wrap"></div>
			</div>
			<div class="issue-resolve-wrap">
				<div class="issue-sub-track-title">
					<span class="text">Resolve</span>
				</div>
				<div class="issue-item-wrap issue-resolve-item-wrap"></div>
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
	<CreateIssueTemplate/>

	<!-- 이슈 편집 템플릿  -->
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
			<button class="create-issue-btn button" data-obj="render" data-cmd="updateIssue">확인</button>
			<button id="cancel-edit-issue" class="cancel-issue-btn" data-obj="render" data-cmd="cancelEditIssue">취소</button>
		</div>
	</div>

	<textarea id="template-new-issue" class="hide">
    <div class="issue-card-title issue-item-attr">
		    <span class="issue-card-title-icon"></span>
		    <span class="issue-card-title-text"></span>
		    </div>
		    <div class="issue-card-type issue-item-attr"></div>
		    <div class="issue-card-user issue-item-attr"></div>
		    <div class="issue-card-version issue-item-attr"></div>
		    <div class="issue-card-label issue-item-attr"></div>
</textarea>

	<textarea id="template-table-item" class="hide">
    <td class="table-item table-issue-type"></td>
		    <td class="table-item table-issue-project">PRJ-1</td>
		    <td class="table-item table-issue-owner"></td>
		    <td class="table-item table-issue-reporter"></td>
		    <td class="table-item table-issue-rank">주요</td>
		    <td class="table-item table-issue-status">TODO</td>
		    <td class="table-item table-issue-version"></td>
		    <td class="table-item table-issue-modifiedAt">2023/05/05</td>
		    <td class="table-item table-issue-status">해결</td>
		    <td class="table-item table-issue-title"></td>
		    <td class="table-item table-issue-createdAt"></td>
</textarea>
</section>
