package org.src.todojpa.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.src.todojpa.domain.dto.comment.CommentResponseDto;
import org.src.todojpa.domain.entity.Comment;
import org.src.todojpa.domain.entity.Schedule;
import org.src.todojpa.domain.entity.User;
import org.src.todojpa.domain.entity.UserRole;
import org.src.todojpa.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ScheduleService scheduleService;

    public Page<CommentResponseDto> retrieveComments(Long scheduleId, Pageable pageable) {
        this.scheduleService.validateSchedule(scheduleId);
        Page<Comment> comments = this.commentRepository.findCommentsByScheduleId(scheduleId, pageable);

        List<CommentResponseDto> commentResponseDtos = comments.getContent().stream()
                .map(CommentResponseDto::from)
                .toList();

        return new PageImpl<>(commentResponseDtos, pageable, comments.getTotalPages());
    }

    public CommentResponseDto retrieveCommentById(Long commentId, Long scheduleId) {
        this.scheduleService.validateSchedule(scheduleId);
        Comment comment = findComment(commentId);

        return CommentResponseDto.from(comment);
    }

    @Transactional
    public CommentResponseDto createComment(String contents, Long scheduleId, Long userId) {
        Schedule schedule = this.scheduleService.findSchedule(scheduleId);
        User user = this.userService.findUserById(userId);

        Comment comment = Comment.builder()
                .contents(contents)
                .schedule(schedule)
                .user(user)
                .build();

        Comment savedComment = this.commentRepository.save(comment);
        return CommentResponseDto.from(savedComment);
    }

    @Transactional
    public CommentResponseDto updateCommentById(Long commentId, Long scheduleId, String contents, Long userId, UserRole role) {
        this.scheduleService.validateSchedule(scheduleId);

        Comment comment = findComment(commentId);

        if (comment.validateWriter(userId) || role.isAdmin()) {
            comment.validateWriter(userId);
            comment.update(contents);

            return CommentResponseDto.from(comment);
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다.");
    }

    @Transactional
    public CommentResponseDto deleteCommentById(Long commentId, Long scheduleId, Long userId, UserRole role) {
        this.scheduleService.validateSchedule(scheduleId);

        Comment comment = findComment(commentId);

        if (comment.validateWriter(userId) || role.isAdmin()) {
            comment.validateWriter(userId);
            this.commentRepository.delete(comment);

            return CommentResponseDto.from(comment);
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다.");
    }

    private Comment findComment(Long commentId) {
        return this.commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
    }
}



