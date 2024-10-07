package org.src.todojpa.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ScheduleResponseDto {
    private Long id;
    private String username;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
