package org.src.todojpa.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.src.todojpa.domain.dto.schedule.ScheduleResponseDto;
import org.src.todojpa.domain.entity.Schedule;
import org.src.todojpa.domain.entity.User;
import org.src.todojpa.repository.ScheduleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserService userService;

    public Page<ScheduleResponseDto> retrieveSchedules(Pageable pageable) {
        Page<Schedule> schedules = this.scheduleRepository.findAll(pageable);

        List<ScheduleResponseDto> scheduleResponseDtos = schedules.getContent().stream()
                .map(ScheduleResponseDto::from)
                .toList();

        return new PageImpl<>(scheduleResponseDtos, pageable, schedules.getTotalPages());
    }

    public ScheduleResponseDto retrieveScheduleById(Long scheduleId) {
        Schedule schedule = findScheduleById(scheduleId);

        return ScheduleResponseDto.from(schedule);
    }

    public ScheduleResponseDto createSchedule(String title, String contents, Long userId) {
        User user = this.userService.findUserById(userId);
        Schedule schedule = Schedule.builder()
                .title(title)
                .contents(contents)
                .user(user)
                .build();

        Schedule savedSchedule = this.scheduleRepository.save(schedule);

        return ScheduleResponseDto.from(savedSchedule);
    }

    @Transactional
    public ScheduleResponseDto updateScheduleById(Long scheduleId, Long userId, String title, String contents) {
        Schedule schedule = findScheduleById(scheduleId);

        schedule.validateWriterByUserId(userId);

        schedule.update(title, contents);

        return ScheduleResponseDto.from(schedule);
    }


    public ScheduleResponseDto deleteScheduleById(Long scheduleId, Long userId) {
        Schedule schedule = findScheduleById(scheduleId);

        schedule.validateWriterByUserId(userId);

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
