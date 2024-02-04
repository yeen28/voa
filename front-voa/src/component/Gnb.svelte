<script>
	function open() {
		const elCreateIssue = document.getElementById('create-issue');
		const elContents = document.getElementsByClassName('contents')[0];
		const elGnb = document.getElementById('gnb');
		elCreateIssue.classList.remove('hide');
		elGnb.classList.add('hidden');
		elContents.classList.add('hidden');
	}

	function create() {
		fetch('/issue', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
				// 추가적인 헤더 정보 설정 가능
			},
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
			})
	}
</script>

<div id="gnb">
	<div class="search-wrap">
		<div class="search-icon"></div>
		<input type="text" class="search-input border-0" placeholder="Search..."/>
	</div>
	<div>
		<div class="board" data-obj="render" data-cmd="viewBoard"></div>
		<div class="table" data-obj="render" data-cmd="viewTable"></div>
	</div>
	<div id="createIssue" class="button" on:click={open}>CREATE</div>
	<div class="profile-wrap">
		<div id="profile-name">Jake</div>
	</div>
</div>
