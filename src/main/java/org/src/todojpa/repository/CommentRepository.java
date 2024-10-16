package org.src.todojpa.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.src.todojpa.domain.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findCommentsByScheduleId(Long scheduleId, Pageable pageable);

    Optional<Comment> findCommentByScheduleIdAndId(Long scheduleId, Long commentId);
}
