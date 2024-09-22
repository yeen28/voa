package com.project.voa.service;

import com.project.voa.domain.*;
import com.project.voa.domain.Version;
import com.project.voa.dto.IssueDTO;
import com.project.voa.dto.IssueModel;
import com.project.voa.repository.*;
import com.project.voa.error.ErrorCodes;
import jakarta.persistence.*;
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
	public IssueModel createIssue(UserInfo userInfo, IssueDTO issueDTO) {
		IssueType issueType = issueTypeRepository.findById(issueDTO.getTypeId()).orElseThrow(() ->
				new EntityNotFoundException(ErrorCodes.ISSUE_TYPE_NOT_FOUND.name()));
		List<Version> versions = upsertVersions(issueDTO.getVersionNames());
		UserInfo owner = userInfoRepository.findById(issueDTO.getOwnerId()).orElseThrow(() ->
				new EntityNotFoundException(ErrorCodes.OWNER_NOT_FOUND.name()));
		UserInfo reporter = userInfoRepository.findById(issueDTO.getReporterId()).orElseThrow(() ->
				new EntityNotFoundException(ErrorCodes.REPORTER_NOT_FOUND.name()));
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
	 * 이슈 단 건 조회
	 * @param id
	 * @return
	 */
	public IssueModel getIssue(final long id) {
		Issue issue = issueRepository.findById(id).orElseThrow(() ->
				new EntityNotFoundException(ErrorCodes.ISSUE_NOT_FOUND.name()));
		return IssueModel.of(issue);
	}

	/**
	 * 이슈 상태만 업데이트
	 * @param id
	 * @param issueStatus
	 */
	public IssueModel updateIssueStatus(final long id, IssueStatus issueStatus) {
		Issue issue = issueRepository.findById(id).orElseThrow(() ->
				new EntityNotFoundException(ErrorCodes.ISSUE_NOT_FOUND.name()));
		issue.setIssueStatus(issueStatus);

		return IssueModel.of(issueRepository.save(issue));
	}

	/**
	 * 이슈 업데이트
	 * @param id
	 * @param issueDTO
	 */
	public IssueModel updateIssue(final long id, final IssueDTO issueDTO) {
		Issue issue = issueRepository.findById(id).orElseThrow(() ->
				new EntityNotFoundException(ErrorCodes.ISSUE_NOT_FOUND.name()));

		IssueType issueType = issueTypeRepository.findById(issueDTO.getTypeId()).orElseThrow(() ->
				new EntityNotFoundException(ErrorCodes.ISSUE_TYPE_NOT_FOUND.name()));
		List<Version> versions = upsertVersions(issueDTO.getVersionNames());
		List<Label> labels = upsertLabels(issueDTO.getLabelNames());
		UserInfo owner = userInfoRepository.findById(issueDTO.getOwnerId()).orElseThrow(() ->
				new EntityNotFoundException(ErrorCodes.OWNER_NOT_FOUND.name()));

		issue.setTitle(issueDTO.getTitle());
		issue.setDescription(issueDTO.getDescription());
		issue.setIssueStatus(issueDTO.getIssueStatus());
		issue.setEnv(issueDTO.getEnv());
		issue.setIssueType(issueType);
		issue.setVersions(versions);
		issue.setLabels(labels);
		issue.setIssueLink(issueDTO.getIssueLink());
		issue.setIssueLinkType(issueDTO.getIssueLinkType());
		issue.setRank(issueDTO.getRank());
		issue.setOwner(owner);
		// TODO attachment, project는 추후 작업 예정

		return IssueModel.of(issueRepository.save(issue));
	}

	/**
	 * issue 삭제
	 * @param id
	 */
	public void deleteIssue(final long id) {
		Issue issue = issueRepository.findById(id).orElseThrow(() ->
				new EntityNotFoundException(ErrorCodes.ISSUE_NOT_FOUND.name()));
		issueRepository.delete(issue);
	}

	/**
	 * 사용자에게 할당된 이슈들 조회
	 * @param ownerId
	 * @return
	 */
	public List<IssueModel> getIssues(final long ownerId) {
		return IssueModel.of(issueRepository.findByOwnerId(ownerId));
	}
}
