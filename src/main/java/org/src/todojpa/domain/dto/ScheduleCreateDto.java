package org.src.todojpa.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ScheduleCreateDto {
    @NotBlank(message = "제목은 필수 입니다.")
    private String title;

    @NotBlank(message = "본문은 필수 입니다.")
    private String contents;

    @NotBlank(message = "userId는 필수 입니다.")
    private Long userId;
}
