package org.src.todojpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.src.todojpa.domain.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
