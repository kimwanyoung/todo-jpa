package org.src.todojpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.src.todojpa.domain.dto.comment.CommentCreateDto;
import org.src.todojpa.domain.dto.comment.CommentResponseDto;
import org.src.todojpa.domain.dto.comment.CommentUpdateDto;
import org.src.todojpa.domain.entity.Schedule;
import org.src.todojpa.domain.entity.User;
import org.src.todojpa.service.CommentService;
import org.src.todojpa.service.ScheduleService;
import org.src.todojpa.service.UserService;

@RestController
@RequestMapping("/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final ScheduleService scheduleService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<PagedModel<CommentResponseDto>> retrieveComments(
            @PathVariable Long scheduleId,
            @PageableDefault Pageable pageable
    ) {
        this.scheduleService.validateScheduleExists(scheduleId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new PagedModel<>(this.commentService.retrieveComments(scheduleId, pageable)));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> retrieveComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId
    ) {
        this.scheduleService.validateScheduleExists(scheduleId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.commentService.retrieveCommentById(commentId));
    }

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long scheduleId,
            @RequestBody CommentCreateDto req
    ) {

        String contents = req.getContents();
        Long userId = req.getUserId();
        User user = this.userService.findUserById(userId);
        Schedule schedule = this.scheduleService.findScheduleById(scheduleId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.commentService.createComment(contents, schedule, user));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @RequestBody CommentUpdateDto req
    ) {
        String contents = req.getContents();
        Long userId = req.getUserId();

        this.scheduleService.validateScheduleExists(scheduleId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.commentService.updateCommentById(commentId, userId, contents));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> deleteComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId
    ) {
        this.scheduleService.validateScheduleExists(scheduleId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.commentService.deleteCommentById(commentId));
    }
}
