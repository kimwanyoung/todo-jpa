package org.src.todojpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.src.todojpa.domain.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
