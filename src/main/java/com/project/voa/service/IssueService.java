package com.project.voa.service;

import com.project.voa.domain.*;
import com.project.voa.dto.IssueDTO;
import com.project.voa.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueService {
	private final IssueRepository issueRepository;
	private final IssueTypeRepository issueTypeRepository;
	private final VersionRepository versionRepository;
	private final UserInfoRepository userInfoRepository;
	private final LabelRepository labelRepository;

	/**
	 * 이슈 생성
	 */
	public Issue create(IssueDTO issueDTO) {
		IssueType issueType = issueTypeRepository.findById(issueDTO.getIssueTypeId()).orElseThrow(EntityNotFoundException::new);
		List<Version> versions = versionRepository.findByIdIn(issueDTO.getVersionIds());
		UserInfo owner = userInfoRepository.findById(issueDTO.getOwnerId()).orElseThrow(EntityNotFoundException::new);
		UserInfo reporter = userInfoRepository.findById(issueDTO.getReporterId()).orElseThrow(EntityNotFoundException::new);
		List<Label> labels = labelRepository.findByIdIn(issueDTO.getLabelIds());
		Issue issue = Issue.of(issueDTO, issueType, versions, owner, reporter, labels);
		return issueRepository.save(issue);
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

	/**
	 * 사용자에게 할당된 이슈들 조회
	 * @param ownerId
	 * @return
	 */
	public List<Issue> getIssues(final long ownerId) {
		return issueRepository.findByOwnerId(ownerId);
	}
}
