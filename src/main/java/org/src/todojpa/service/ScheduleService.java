package org.src.todojpa.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.src.todojpa.domain.dto.schedule.ScheduleResponseDto;
import org.src.todojpa.domain.entity.Schedule;
import org.src.todojpa.domain.entity.User;
import org.src.todojpa.domain.entity.UserRole;
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
        Schedule schedule = findSchedule(scheduleId);

        return ScheduleResponseDto.from(schedule);
    }

    @Transactional
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
    public ScheduleResponseDto updateScheduleById(Long scheduleId, Long userId, UserRole role, String title, String contents) {
        Schedule schedule = findSchedule(scheduleId);

        if (schedule.validateWriter(userId) || role.isAdmin()) {
            schedule.update(title, contents);

            return ScheduleResponseDto.from(schedule);
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다.");
    }


    public ScheduleResponseDto deleteScheduleById(Long scheduleId, Long userId, UserRole role) {
        Schedule schedule = findSchedule(scheduleId);

        if (schedule.validateWriter(userId) || role.isAdmin()) {
            this.scheduleRepository.delete(schedule);

            return ScheduleResponseDto.from(schedule);
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다.");
    }

    public void validateSchedule(Long scheduleId) {
        if (!scheduleRepository.existsById(scheduleId)) {
            throw new IllegalArgumentException("존재하지 않는 일정입니다.");
        }
    }

    public Schedule findSchedule(Long id) {
        return this.scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정입니다."));
    }
}
