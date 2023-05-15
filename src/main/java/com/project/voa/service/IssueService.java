package com.project.voa.service;

import com.project.voa.domain.Issue;
import com.project.voa.repository.IssueRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IssueService {
	private final IssueRepository issueRepository;

	/**
	 * 이슈 생성
	 */
	public void create() {
		// TODO 파라미터 하드코딩
		Issue issue = new Issue();
		issue.setTitle("버그 수정이 필요합니다.");
		issue.setRank(1);
		issue.setEnv("Windows11");
		issue.setDescription("설명입니다.");
		issue.setIssueLinkType(1000);
		issue.setIssueLink("link");

		issueRepository.save(issue);
	}

	/**
	 * issue 얻기
	 * @param id
	 * @return
	 */
	public Issue getIssue(final long id) {
		return issueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	/**
	 * issue 업데이트
	 * @param id
	 */
	public void update(final long id) {
		// TODO 파라미터 하드코딩
		// TODO linkType을 +1씩 증가로 update. 이후 수정 예정.
		Issue issue = issueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		issue.setIssueLinkType(issue.getIssueLinkType() + 1);

		issueRepository.save(issue);
	}

	/**
	 * issue 삭제
	 * @param id
	 */
	public void delete(final long id) {
		// TODO 파라미터 하드코딩
		Issue issue = issueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		issueRepository.delete(issue);
	}
}
