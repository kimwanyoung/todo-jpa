package org.src.todojpa.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.src.todojpa.domain.dto.ScheduleCreateDto;
import org.src.todojpa.domain.dto.ScheduleResponseDto;
import org.src.todojpa.domain.dto.ScheduleUpdateDto;
import org.src.todojpa.domain.entity.User;
import org.src.todojpa.dto.ScheduleDeleteDto;
import org.src.todojpa.service.ScheduleService;
import org.src.todojpa.service.UserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final UserService userService;

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
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody @Valid ScheduleCreateDto req) {
        User user = this.userService.findUserById(req.getUserId());
        String contents = req.getContents();
        String title = req.getTitle();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.scheduleService.createSchedule(title, contents, user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody @Valid ScheduleUpdateDto req
    ) {
        Long userId = req.getUserId();
        String title = req.getTitle();
        String contents = req.getContents();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.scheduleService.updateScheduleById(id, userId, title, contents));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> deleteSchedule(
            @PathVariable Long id,
            @RequestBody @Valid ScheduleDeleteDto req
            ) {
        Long userId = req.getUserId();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.scheduleService.deleteScheduleById(id, userId));
    }
}
