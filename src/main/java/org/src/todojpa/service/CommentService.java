package org.src.todojpa.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.src.todojpa.domain.dto.CommentCreateDto;
import org.src.todojpa.domain.dto.CommentResponseDto;
import org.src.todojpa.domain.entity.Comment;
import org.src.todojpa.domain.entity.Schedule;
import org.src.todojpa.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleService scheduleService;

    public Page<CommentResponseDto> retrieveComments(Long scheduleId, Pageable pageable) {
        this.scheduleService.validateScheduleExists(scheduleId);
        Page<Comment> comments = this.commentRepository.findAll(pageable);

        List<CommentResponseDto> commentResponseDtos = comments.getContent().stream()
                .map(comment -> CommentResponseDto.builder()
                        .id(comment.getId())
                        .username(comment.getUsername())
                        .contents(comment.getContents())
                        .createdAt(comment.getCreatedAt())
                        .modifiedAt(comment.getModifiedAt())
                        .build())
                .toList();

        return new PageImpl<>(commentResponseDtos, pageable, comments.getTotalPages());
    }

    public CommentResponseDto retrieveCommentById(Long scheduleId, Long commentId) {
        this.scheduleService.validateScheduleExists(scheduleId);
        Comment comment = findCommentById(commentId);

        return CommentResponseDto.from(comment);
    }

    @Transactional
    public CommentResponseDto createComment(Long scheduleId, CommentCreateDto req) {
        Schedule schedule = this.scheduleService.findSchedule(scheduleId);
        Comment comment = Comment.builder()
                .contents(req.getContents())
                .schedule(schedule)
                .username(req.getUsername())
                .build();

        Comment savedComment = this.commentRepository.save(comment);
        return CommentResponseDto.from(savedComment);
    }

    private Comment findCommentById(Long commentId) {
        return this.commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
    }
}



