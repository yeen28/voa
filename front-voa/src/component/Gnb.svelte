<script>
	function open() {
		const elCreateIssue = document.getElementById('create-issue');
		const elContents = document.getElementsByClassName('contents')[0];
		const elGnb = document.getElementById('gnb');
		elCreateIssue.classList.remove('hide');
		elGnb.classList.add('hidden');
		elContents.classList.add('hidden');
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
