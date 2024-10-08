package org.src.todojpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.src.todojpa.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
