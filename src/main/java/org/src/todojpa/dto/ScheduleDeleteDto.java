package org.src.todojpa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ScheduleDeleteDto {
    @NotBlank(message = "userId는 필수 입니다.")
    private Long userId;
}
