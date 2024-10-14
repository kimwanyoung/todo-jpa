package org.src.todojpa.domain.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class ScheduleUpdateDto {
    @NotNull(message = "userId는 필수 입니다.")
    private Long userId;

    private String title;

    private String contents;
}
