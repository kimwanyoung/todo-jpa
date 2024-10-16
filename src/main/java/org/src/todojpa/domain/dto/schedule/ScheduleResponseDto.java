package org.src.todojpa.domain.dto.schedule;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.src.todojpa.domain.dto.user.UserResponseDto;
import org.src.todojpa.domain.entity.Schedule;

@Getter
@Setter
@Builder
public class ScheduleResponseDto {

    private Long id;
    private String title;
    private String contents;
    private Integer commentsSize;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private UserResponseDto user;

    public static ScheduleResponseDto from(Schedule schedule) {
        UserResponseDto user = UserResponseDto.from(schedule.getUser());
        int commentsSize = schedule.getComments() == null ? 0 : schedule.getComments().size();

        return ScheduleResponseDto.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .contents(schedule.getContents())
                .commentsSize(commentsSize)
                .user(user)
                .createdAt(schedule.getCreatedAt())
                .modifiedAt(schedule.getModifiedAt())
                .build();
    }
}
