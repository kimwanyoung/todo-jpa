package org.src.todojpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.src.todojpa.domain.entity.UserSchedule;

public interface UserScheduleRepository extends JpaRepository<UserSchedule, Long> {

    List<UserSchedule> findByScheduleId(Long id);
}
