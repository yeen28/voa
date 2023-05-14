package com.project.voa.service;

import com.project.voa.domain.Label;
import com.project.voa.repository.LabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LabelService {
	private final LabelRepository labelRepository;

	/**
	 * labels 조회
	 * @return
	 */
	public Iterable<Label> getLabels() {
		return labelRepository.findAll();
	}
}
