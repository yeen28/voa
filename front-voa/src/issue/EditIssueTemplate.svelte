<script>
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
		<button class="create-issue-btn button" data-obj="render" data-cmd="updateIssue">확인</button>
		<button id="cancel-edit-issue" class="cancel-issue-btn" data-obj="render" data-cmd="cancelEditIssue">취소</button>
	</div>
</div>