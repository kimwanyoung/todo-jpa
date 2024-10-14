package org.src.todojpa.domain.dto.schedule;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ScheduleDeleteDto {
    @NotNull(message = "userId는 필수 입니다.")
    private Long userId;
}
