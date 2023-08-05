package com.project.voa.controller;

import com.project.voa.dto.IssueDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class IssueControllerTest {
	private Validator validator = null;

	@BeforeEach
	public void setupValidator() {
		// Validator 생성
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}
	@Test
	void 사용자_이름_DTO_NotEmpty_체크() {
		//given
		IssueDTO issueDTO = new IssueDTO();
		issueDTO.setTitle("");
		issueDTO.setIssueTypeId(1L);

		//when
		Set<ConstraintViolation<IssueDTO>> violations = validator.validate(issueDTO);

		//then
		assertThat(violations.stream().anyMatch(el -> !el.getMessageTemplate().isBlank())).isTrue(); // 오류 메시지가 존재하는지 체크
	}
}