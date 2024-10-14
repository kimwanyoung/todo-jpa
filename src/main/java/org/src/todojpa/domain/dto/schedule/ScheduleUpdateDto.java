package org.src.todojpa.domain.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class ScheduleUpdateDto {
    private String title;

    private String contents;
}
