package org.src.todojpa.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.src.todojpa.domain.dto.schedule.ScheduleCreateDto;
import org.src.todojpa.domain.dto.schedule.ScheduleResponseDto;
import org.src.todojpa.domain.dto.schedule.ScheduleUpdateDto;
import org.src.todojpa.service.ScheduleService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<PagedModel<ScheduleResponseDto>> retrieveSchedules(@PageableDefault Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new PagedModel<>(this.scheduleService.retrieveSchedules(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> retrieveSchedule(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.scheduleService.retrieveScheduleById(id));
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestAttribute("userId") Long userId,
            @RequestBody @Valid ScheduleCreateDto req
    ) {
        String contents = req.getContents();
        String title = req.getTitle();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.scheduleService.createSchedule(title, contents, userId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @RequestAttribute("userId") Long userId,
            @PathVariable Long id,
            @RequestBody ScheduleUpdateDto req
    ) {
        String title = req.getTitle();
        String contents = req.getContents();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.scheduleService.updateScheduleById(id, userId, title, contents));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> deleteSchedule(
            @RequestAttribute("userId") Long userId,
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.scheduleService.deleteScheduleById(id, userId));
    }
}
