package org.src.todojpa.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.src.todojpa.domain.dto.schedule.AddManagerDto;
import org.src.todojpa.domain.dto.schedule.ScheduleCreateDto;
import org.src.todojpa.domain.dto.schedule.ScheduleResponseDto;
import org.src.todojpa.domain.dto.schedule.ScheduleUpdateDto;
import org.src.todojpa.domain.dto.user.VerifiedUserDto;
import org.src.todojpa.domain.entity.UserRole;
import org.src.todojpa.service.ScheduleService;

@Slf4j(topic = "schedule controller")
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
        UserRole role = verifiedUserDto.getRole();

        ScheduleResponseDto scheduleResponseDto = this.scheduleService.updateScheduleById(id, userId, role, title,
                contents);

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
        UserRole role = verifiedUserDto.getRole();

        ScheduleResponseDto scheduleResponseDto = this.scheduleService.deleteScheduleById(id, userId, role);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduleResponseDto);
    }

    @PostMapping("/{id}/manager")
    public ResponseEntity<Long> addManager(
            @PathVariable Long id,
            @RequestBody AddManagerDto req,
            VerifiedUserDto verifiedUserDto
    ) {
        Long authorId = verifiedUserDto.getUserId();
        Long managerId = req.getManagerId();

        Long addedUserId = this.scheduleService.addManagerToSchedule(id, authorId, managerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addedUserId);
    }
}
