package org.src.todojpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.src.todojpa.domain.dto.ScheduleCreateDto;
import org.src.todojpa.domain.dto.ScheduleResponseDto;
import org.src.todojpa.domain.dto.ScheduleUpdateDto;
import org.src.todojpa.service.ScheduleService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<PagedModel<ScheduleResponseDto>> retrieveSchedules(Pageable pageable) {
        return ResponseEntity.ok(new PagedModel<>(this.scheduleService.retrieveSchedules(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> retrieveSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(this.scheduleService.retrieveScheduleById(id));
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleCreateDto req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.scheduleService.createSchedule(req));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleUpdateDto req
    ) {
        return ResponseEntity.ok(this.scheduleService.updateScheduleById(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> deleteSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(this.scheduleService.deleteScheduleById(id));
    }
}
