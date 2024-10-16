package org.src.todojpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.src.todojpa.domain.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
