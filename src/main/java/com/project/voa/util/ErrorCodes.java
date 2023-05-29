package com.project.voa.util;

import io.swagger.v3.oas.annotations.media.Schema;

public enum ErrorCodes {
    @Schema(description = "이메일 중복")
    DUPLICATED_EMAIL,

    @Schema(description = "존재하지 않는 이슈")
    ISSUE_NOT_FOUND
}