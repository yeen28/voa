<script>
	export let issue;
	export let onClick;
	export let onDragStart;
	export let onDragEnd;

	function handleClick() {
		onClick(issue.id);
	}

	function handleDragStart(event) {
		onDragStart(issue.id, event);
	}

	function handleDragEnd(event) {
		onDragEnd(issue.id, event);
	}
</script>

<div
		class="issue-card"
		data-id={issue.id}
		on:click={handleClick}
		draggable="true"
		on:dragstart={handleDragStart}
		on:dragend={handleDragEnd}
>
	<div class="issue-card-title issue-item-attr">
		<span class="issue-card-title-icon"></span>
		<span class="issue-card-title-text">{issue.title}</span>
	</div>
	<div class="issue-card-type issue-item-attr">{issue.issueType}</div>
	<div class="issue-card-user issue-item-attr">{issue.ownerName}</div>
	<div class="issue-card-version issue-item-attr">{issue.versionNames}</div>
	<div class="issue-card-label issue-item-attr">{issue.labelNames}</div>
</div>

<style>
	.issue-card {
		cursor: grab;
		width: initial;
	}
</style>