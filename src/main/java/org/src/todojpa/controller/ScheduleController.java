package org.src.todojpa.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.src.todojpa.domain.dto.schedule.ScheduleCreateDto;
import org.src.todojpa.domain.dto.schedule.ScheduleResponseDto;
import org.src.todojpa.domain.dto.schedule.ScheduleUpdateDto;
import org.src.todojpa.domain.dto.user.VerifiedUserDto;
import org.src.todojpa.service.ScheduleService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<PagedModel<ScheduleResponseDto>> retrieveSchedules(@PageableDefault Pageable pageable) {
        Page<ScheduleResponseDto> scheduleResponseDtos = this.scheduleService.retrieveSchedules(pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new PagedModel<>(scheduleResponseDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> retrieveSchedule(@PathVariable Long id) {
        ScheduleResponseDto scheduleResponseDto = this.scheduleService.retrieveScheduleById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduleResponseDto);
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestBody @Valid ScheduleCreateDto req,
            VerifiedUserDto verifiedUserDto
    ) {
        String contents = req.getContents();
        String title = req.getTitle();
        Long userId = verifiedUserDto.getUserId();

        ScheduleResponseDto scheduleResponseDto = this.scheduleService.createSchedule(title, contents, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(scheduleResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleUpdateDto req,
            VerifiedUserDto verifiedUserDto
    ) {
        String title = req.getTitle();
        String contents = req.getContents();
        Long userId = verifiedUserDto.getUserId();

        ScheduleResponseDto scheduleResponseDto = this.scheduleService.updateScheduleById(id, userId, title, contents);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduleResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> deleteSchedule(
            @PathVariable Long id,
            VerifiedUserDto verifiedUserDto
    ) {
        Long userId = verifiedUserDto.getUserId();

        ScheduleResponseDto scheduleResponseDto = this.scheduleService.deleteScheduleById(id, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduleResponseDto);
    }
}
