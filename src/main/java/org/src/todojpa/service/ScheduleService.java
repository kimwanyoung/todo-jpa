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
import org.src.todojpa.domain.entity.Schedule;
import org.src.todojpa.repository.ScheduleRepository;

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
                        .contents(schedule.getContents())
                        .username(schedule.getUsername())
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

    public ScheduleResponseDto createSchedule(ScheduleCreateDto req) {
        Schedule schedule = Schedule.from(req);

        Schedule savedSchedule = this.scheduleRepository.save(schedule);

        return ScheduleResponseDto.from(savedSchedule);
    }

    @Transactional
    public ScheduleResponseDto updateScheduleById(Long id, ScheduleUpdateDto req) {
        Schedule schedule = findSchedule(id);

        schedule.update(req);

        return ScheduleResponseDto.from(schedule);
    }


    public ScheduleResponseDto deleteScheduleById(Long id) {
        Schedule schedule = findSchedule(id);

        // TODO: 추후 comment entity 추가되면, 댓글도 함께 삭제되어야함
        this.scheduleRepository.delete(schedule);

        return ScheduleResponseDto.from(schedule);
    }

    private Schedule findSchedule(Long id) {
        return this.scheduleRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정입니다."));
    }
}
