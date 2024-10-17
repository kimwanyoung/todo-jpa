package org.src.todojpa.domain.dto.schedule;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.src.todojpa.domain.dto.user.UserResponseDto;
import org.src.todojpa.domain.entity.Schedule;
import org.src.todojpa.domain.entity.UserSchedule;

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
    private UserResponseDto author;
    private List<UserResponseDto> managers;

    public static ScheduleResponseDto from(Schedule schedule) {
        UserResponseDto user = UserResponseDto.from(schedule.getAuthor());
        int commentsSize = schedule.getComments() == null ? 0 : schedule.getComments().size();

        return ScheduleResponseDto.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .contents(schedule.getContents())
                .commentsSize(commentsSize)
                .author(user)
                .createdAt(schedule.getCreatedAt())
                .modifiedAt(schedule.getModifiedAt())
                .build();
    }

    public static ScheduleResponseDto of(Schedule schedule, List<UserSchedule> userSchedules) {
        UserResponseDto author = UserResponseDto.from(schedule.getAuthor());
        List<UserResponseDto> managers = userSchedules.stream()
                .map(UserSchedule::getManager)
                .map(UserResponseDto::from)
                .toList();

        int commentsSize = schedule.getComments() == null ? 0 : schedule.getComments().size();

        return ScheduleResponseDto.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .contents(schedule.getContents())
                .commentsSize(commentsSize)
                .author(author)
                .managers(managers)
                .createdAt(schedule.getCreatedAt())
                .modifiedAt(schedule.getModifiedAt())
                .build();
    }
}
