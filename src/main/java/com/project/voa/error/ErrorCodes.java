package com.project.voa.error;

import io.swagger.v3.oas.annotations.media.Schema;

public enum ErrorCodes {
	@Schema(description = "이메일 중복")
	DUPLICATED_EMAIL,

	@Schema(description = "존재하지 않는 이슈")
	ISSUE_NOT_FOUND,

	@Schema(description = "존재하지 않는 이슈 유형")
	ISSUE_TYPE_NOT_FOUND,

	@Schema(description = "존재하지 않는 담당자")
	OWNER_NOT_FOUND,

	@Schema(description = "존재하지 않는 보고자")
	REPORTER_NOT_FOUND,

	@Schema(description = "존재하지 않는 버전")
	VERSION_NOT_FOUND,

	@Schema(description = "존재하지 않는 파일")
	FILE_NOT_FOUND,

	@Schema(description = "아무것도 업로드하지 않은 경우")
	FILE_IS_NULL
}