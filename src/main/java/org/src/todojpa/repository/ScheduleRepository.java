package org.src.todojpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.src.todojpa.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Page<Schedule> findAllBy(Pageable pageable);
}
