<script>
	import Gnb from "../component/Gnb.svelte";
	import CreateIssueTemplate from "./CreateIssueTemplate.svelte";
	import EditIssueTemplate from "./EditIssueTemplate.svelte";

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
		elIssueCard.addEventListener('click', clickIssue);
		elIssueCard.innerHTML = (document.getElementById('template-new-issue')).value;

		elIssueCard.getElementsByClassName('issue-card-title-text')[0].textContent = issue.title;
		elIssueCard.getElementsByClassName('issue-card-type')[0].textContent = issue.issueType;
		elIssueCard.getElementsByClassName('issue-card-user')[0].textContent = issue.ownerName;
		elIssueCard.getElementsByClassName('issue-card-version')[0].textContent = issue.versionNames;
		elIssueCard.getElementsByClassName('issue-card-label')[0].textContent = issue.labelNames;

		document.getElementsByClassName('issue-todo-item-wrap')[0].appendChild(elIssueCard)
	}

	function clickIssue() {
		const id = ((event.target).closest('.issue-card') || (event.target).closest('.row')).getAttribute('data-id');

		fetch(`/issue/${id}`)
			.then(response => response.json())
			.then(issue => {
				const elEditIssue = document.getElementById('edit-issue');
				elEditIssue.setAttribute('data-issue-id', id);
				(document.getElementById('issue-edit-title-input')).value = issue.title;
				(document.getElementById('issue-edit-version-input')).value = issue.versionNames.join(',');
				(document.getElementById('issue-edit-owner-select')).value = issue.ownerName;
				(document.getElementById('issue-edit-env-content')).value = issue.env;
				(document.getElementById('issue-edit-desc-content')).value = issue.description;
				(document.getElementById('issue-edit-label-input')).value = issue.labelNames.join(',');
				elEditIssue.classList.remove('hide');
			})
			.catch(error => console.log(error));
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
	<EditIssueTemplate/>

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
