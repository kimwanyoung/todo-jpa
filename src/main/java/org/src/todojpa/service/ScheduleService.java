package org.src.todojpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.src.todojpa.dto.ScheduleResponseDto;
import org.src.todojpa.entity.Schedule;
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
    }
}
