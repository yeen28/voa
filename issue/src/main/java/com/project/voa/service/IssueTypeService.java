package com.project.voa.service;

import com.project.voa.domain.IssueType;
import com.project.voa.dto.IssueTypeModel;
import com.project.voa.repository.IssueTypeRepository;
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
