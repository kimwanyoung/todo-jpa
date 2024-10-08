package org.src.todojpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.src.todojpa.domain.dto.CommentResponseDto;
import org.src.todojpa.domain.entity.Comment;
import org.src.todojpa.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleService scheduleService;

    public CommentResponseDto retrieveCommentById(Long scheduleId, Long commentId) {
        this.scheduleService.validateScheduleExists(scheduleId);
        Comment comment = findCommentById(commentId);

        return CommentResponseDto.from(comment);
    }

    private Comment findCommentById(Long commentId) {
        return this.commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
    }
}
