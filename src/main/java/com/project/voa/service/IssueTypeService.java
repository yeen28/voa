package com.project.voa.service;

import com.project.voa.domain.IssueType;
import com.project.voa.repository.IssueTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueTypeService {
	private final IssueTypeRepository issueTypeRepository;

	/**
	 * issueType 생성
	 */
	public void create() {
		// TODO 파라미터 하드코딩
		List<IssueType> listIssueType = new ArrayList<>();

		IssueType issueType = new IssueType();
		issueType.setName("버그");
		listIssueType.add(issueType);

		issueType = new IssueType();
		issueType.setName("작업");
		listIssueType.add(issueType);

		issueType = new IssueType();
		issueType.setName("개선사항");
		listIssueType.add(issueType);

		issueType = new IssueType();
		issueType.setName("스토리");
		listIssueType.add(issueType);

		issueTypeRepository.saveAll(listIssueType);
	}
}
