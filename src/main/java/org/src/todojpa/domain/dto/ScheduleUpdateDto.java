package org.src.todojpa.domain.dto;

import lombok.Getter;

@Getter
public class ScheduleUpdateDto {
    private String title;
    private String contents;
    private Long userId;
}
