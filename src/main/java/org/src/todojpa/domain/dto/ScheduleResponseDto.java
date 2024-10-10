package org.src.todojpa.domain.dto;

import lombok.*;
import org.src.todojpa.domain.entity.Schedule;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private UserResponseDto user;

    public static ScheduleResponseDto from(Schedule schedule) {
        return ScheduleResponseDto.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .contents(schedule.getContents())
                .user(UserResponseDto.from(schedule.getUser()))
                .createdAt(schedule.getCreatedAt())
                .modifiedAt(schedule.getModifiedAt())
                .build();
    }
}
