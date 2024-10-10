package org.src.todojpa.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.src.todojpa.domain.dto.ScheduleCreateDto;
import org.src.todojpa.domain.dto.ScheduleResponseDto;
import org.src.todojpa.domain.dto.ScheduleUpdateDto;
import org.src.todojpa.domain.dto.UserResponseDto;
import org.src.todojpa.domain.entity.Schedule;
import org.src.todojpa.domain.entity.User;
import org.src.todojpa.dto.ScheduleDeleteDto;
import org.src.todojpa.repository.ScheduleRepository;
import org.src.todojpa.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public Page<ScheduleResponseDto> retrieveSchedules(Pageable pageable) {
        Page<Schedule> schedules = this.scheduleRepository.findAll(pageable);

        List<ScheduleResponseDto> scheduleResponseDtos = schedules.getContent().stream()
                .map(schedule -> ScheduleResponseDto.builder()
                        .id(schedule.getId())
                        .title(schedule.getTitle())
                        .user(UserResponseDto.from(schedule.getUser()))
                        .contents(schedule.getContents())
                        .createdAt(schedule.getCreatedAt())
                        .modifiedAt(schedule.getModifiedAt())
                        .build())
                .toList();

        return new PageImpl<>(scheduleResponseDtos, pageable, schedules.getTotalPages());
    }

    public ScheduleResponseDto retrieveScheduleById(Long id) {
        Schedule schedule = findSchedule(id);

        return ScheduleResponseDto.from(schedule);
    }

    public ScheduleResponseDto createSchedule(ScheduleCreateDto req, User user) {
        Schedule schedule = Schedule.builder()
                .title(req.getTitle())
                .contents(req.getContents())
                .user(user)
                .build();

        Schedule savedSchedule = this.scheduleRepository.save(schedule);

        return ScheduleResponseDto.from(savedSchedule);
    }

    @Transactional
    public ScheduleResponseDto updateScheduleById(Long id, ScheduleUpdateDto req) {
        Schedule schedule = findSchedule(id);

        schedule.validateUserById(req.getUserId());

        schedule.update(req);

        return ScheduleResponseDto.from(schedule);
    }


    public ScheduleResponseDto deleteScheduleById(Long id, ScheduleDeleteDto req) {
        Schedule schedule = findSchedule(id);

        schedule.validateUserById(req.getUserId());

        this.scheduleRepository.delete(schedule);

        return ScheduleResponseDto.from(schedule);
    }

    public void validateScheduleExists(Long scheduleId) {
        if (!scheduleRepository.existsById(scheduleId)) {
            throw new IllegalArgumentException("존재하지 않는 일정입니다.");
        }
    }

    public Schedule findSchedule(Long id) {
        return this.scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정입니다."));
    }
}
