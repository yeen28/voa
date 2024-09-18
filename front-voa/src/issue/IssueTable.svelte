<script>
	import {onMount} from 'svelte';

	let issues = [];

	onMount(() => {
		fetch('/issues', {
			method: 'GET'
		})
			.then(res => res.json())
			.then(res => {
				issues = res;
			})
			.catch(e => console.error(e));
	})
</script>

<table id="issue-table">
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
	<tbody id="issue-table-tbody">
	{#each issues as issue (issue.id)}
		<tr class="row" data-id={issue.id} data-obj="render" data-cmd="clickIssueEvent">
			<td class="table-item table-issue-type">{issue.issueType}</td>
			<td class="table-item table-issue-project">PRJ-1</td>
			<td class="table-item table-issue-owner">{issue.ownerName}</td>
			<td class="table-item table-issue-reporter">{issue.ownerName}</td>
			<td class="table-item table-issue-rank">주요</td>
			<td class="table-item table-issue-status">{issue.issueStatus}</td>
			<td class="table-item table-issue-version">{issue.versionNames}</td>
			<td class="table-item table-issue-modifiedAt">2023/05/05</td>
			<td class="table-item table-issue-status">해결</td>
			<td class="table-item table-issue-title">{issue.title}</td>
			<td class="table-item table-issue-createdAt">{issue.createdAt}</td>
		</tr>
	{/each}
	</tbody>
</table>