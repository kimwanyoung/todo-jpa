package org.src.todojpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.src.todojpa.entity.Schedule;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<Page<List<Schedule>>> retrieveSchedules(Pageable pageable) {
        return ResponseEntity.ok(this.scheduleService.retrieveSchedules(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> retrieveSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(this.scheduleService.retrieveScheduleById(id));
    }

    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.scheduleService.createSchedule());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(
            @PathVariable Long id,
            @RequestBody Schedule schedule
    ) {
        return ResponseEntity.ok(this.scheduleService.updateScheduleById(id, schedule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Schedule> deleteSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(this.scheduleService.deleteScheduleById(id));
    }
}
