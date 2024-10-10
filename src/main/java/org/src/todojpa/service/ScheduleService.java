package org.src.todojpa.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.src.todojpa.domain.dto.ScheduleResponseDto;
import org.src.todojpa.domain.dto.UserResponseDto;
import org.src.todojpa.domain.entity.Schedule;
import org.src.todojpa.domain.entity.User;
import org.src.todojpa.repository.ScheduleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public Page<ScheduleResponseDto> retrieveSchedules(Pageable pageable) {
        Page<Schedule> schedules = this.scheduleRepository.findAll(pageable);

        List<ScheduleResponseDto> scheduleResponseDtos = schedules.getContent().stream()
                .map(schedule -> {
                    UserResponseDto user = UserResponseDto.from(schedule.getUser());
                    return ScheduleResponseDto.builder()
                            .id(schedule.getId())
                            .title(schedule.getTitle())
                            .user(user)
                            .contents(schedule.getContents())
                            .createdAt(schedule.getCreatedAt())
                            .modifiedAt(schedule.getModifiedAt())
                            .build();
                })
                .toList();

        return new PageImpl<>(scheduleResponseDtos, pageable, schedules.getTotalPages());
    }

    public ScheduleResponseDto retrieveScheduleById(Long id) {
        Schedule schedule = findScheduleById(id);

        return ScheduleResponseDto.from(schedule);
    }

    public ScheduleResponseDto createSchedule(String title, String contents, User user) {
        Schedule schedule = Schedule.builder()
                .title(title)
                .contents(contents)
                .user(user)
                .build();

        Schedule savedSchedule = this.scheduleRepository.save(schedule);

        return ScheduleResponseDto.from(savedSchedule);
    }

    @Transactional
    public ScheduleResponseDto updateScheduleById(Long id, Long userId, String title, String contents) {
        Schedule schedule = findScheduleById(id);

        schedule.validateUserById(userId);

        schedule.update(title, contents);

        return ScheduleResponseDto.from(schedule);
    }


    public ScheduleResponseDto deleteScheduleById(Long id, Long userId) {
        Schedule schedule = findScheduleById(id);

        schedule.validateUserById(userId);

        this.scheduleRepository.delete(schedule);

        return ScheduleResponseDto.from(schedule);
    }

    public void validateScheduleExists(Long scheduleId) {
        if (!scheduleRepository.existsById(scheduleId)) {
            throw new IllegalArgumentException("존재하지 않는 일정입니다.");
        }
    }

    public Schedule findScheduleById(Long id) {
        return this.scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정입니다."));
    }
}
