package com.project.issue.service;

import com.project.modulecommon.domain.Label;
import com.project.modulecommon.dto.LabelModel;
import com.project.modulecommon.repository.LabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabelService {
	private final LabelRepository labelRepository;

	/**
	 * label들 조회
	 * @return
	 */
	public List<LabelModel> getLabels() {
		return LabelModel.labelsToLabelModelList((List<Label>) labelRepository.findAll());
	}

	public void deleteLabel(final long id) {
		labelRepository.deleteById(id);
	}
}
