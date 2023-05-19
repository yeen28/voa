package com.project.voa.service;

import com.project.voa.domain.*;
import com.project.voa.dto.IssueDTO;
import com.project.voa.dto.IssueModel;
import com.project.voa.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

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
	public IssueModel createIssue(IssueDTO issueDTO) {
		IssueType issueType = issueTypeRepository.findById(issueDTO.getIssueTypeId()).orElseThrow(EntityNotFoundException::new);
		List<Version> versions = upsertVersions(issueDTO.getVersionNames());
		UserInfo owner = userInfoRepository.findById(issueDTO.getOwnerId()).orElseThrow(EntityNotFoundException::new);
		UserInfo reporter = userInfoRepository.findById(issueDTO.getReporterId()).orElseThrow(EntityNotFoundException::new);
		List<Label> labels = upsertLabels(issueDTO.getLabelNames());

		Issue issue = Issue.of(issueDTO, issueType, versions, owner, reporter, labels);
		issueRepository.save(issue);
		return IssueModel.of(issue);
	}

	/**
	 * version이 있으면 조회, 없으면 저장
	 * @param versionNames
	 * @return
	 */
	private List<Version> upsertVersions(List<String> versionNames) {
		return versionNames.stream()
				.map(name -> versionRepository.findByName(name).orElseGet(() -> {
					Version newVersion = new Version(name);
					versionRepository.save(newVersion);
					return newVersion;
				})).collect(Collectors.toList());
	}

	/**
	 * label이 있으면 조회, 없으면 저장
	 * @param labelNames
	 * @return
	 */
	private List<Label> upsertLabels(List<String> labelNames) {
		return labelNames.stream()
				.map(name -> labelRepository.findByName(name).orElseGet(() -> {
					Label newLabel = new Label(name);
					labelRepository.save(newLabel);
					return newLabel;
				})).collect(Collectors.toList());
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
