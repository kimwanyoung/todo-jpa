package org.src.todojpa.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.src.todojpa.domain.dto.comment.CommentResponseDto;
import org.src.todojpa.domain.entity.Comment;
import org.src.todojpa.domain.entity.Schedule;
import org.src.todojpa.domain.entity.User;
import org.src.todojpa.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Page<CommentResponseDto> retrieveComments(Long scheduleId, Pageable pageable) {
        Page<Comment> comments = this.commentRepository.findCommentsByScheduleId(scheduleId ,pageable);

        List<CommentResponseDto> commentResponseDtos = comments.getContent().stream()
                .map(CommentResponseDto::from)
                .toList();

        return new PageImpl<>(commentResponseDtos, pageable, comments.getTotalPages());
    }

    public CommentResponseDto retrieveCommentById(Long commentId) {
        Comment comment = findCommentById(commentId);

        return CommentResponseDto.from(comment);
    }

    @Transactional
    public CommentResponseDto createComment(String contents, Schedule schedule, User user) {
        Comment comment = Comment.builder()
                .contents(contents)
                .schedule(schedule)
                .user(user)
                .build();

        Comment savedComment = this.commentRepository.save(comment);
        return CommentResponseDto.from(savedComment);
    }

    @Transactional
    public CommentResponseDto updateCommentById(Long commentId, Long userId, String contents) {
        Comment comment = findCommentById(commentId);

        comment.checkUserById(userId);
        comment.update(contents);

        return CommentResponseDto.from(comment);
    }

    public CommentResponseDto deleteCommentById(Long commentId) {
        Comment comment = findCommentById(commentId);

        this.commentRepository.delete(comment);

        return CommentResponseDto.from(comment);
    }

    private Comment findCommentById(Long commentId) {
        return this.commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
    }
}



