package com.project.issue.service;

import com.project.modulecommon.domain.IssueType;
import com.project.modulecommon.dto.IssueTypeModel;
import com.project.modulecommon.repository.IssueTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueTypeService {
	private final IssueTypeRepository issueTypeRepository;

	/**
	 * 이슈 유형들 조회
	 * @return
	 */
	public List<IssueTypeModel> getIssueTypes() {
		List<IssueType> issueTypes = (List<IssueType>) issueTypeRepository.findAll();
		return IssueTypeModel.issueTypesToIssueTypeModels(issueTypes);
	}
}
