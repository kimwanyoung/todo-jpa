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
import org.src.todojpa.service.CommentService;

@RestController
@RequestMapping("/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<PagedModel<CommentResponseDto>> retrieveComments(
            @PathVariable Long scheduleId,
            @PageableDefault Pageable pageable
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new PagedModel<>(this.commentService.retrieveComments(scheduleId, pageable)));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> retrieveComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.commentService.retrieveCommentById(commentId, scheduleId));
    }

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @RequestAttribute("userId") Long userId,
            @PathVariable Long scheduleId,
            @RequestBody CommentCreateDto req
    ) {
        String contents = req.getContents();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.commentService.createComment(contents, scheduleId, userId));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @RequestAttribute("userId") Long userId,
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @RequestBody CommentUpdateDto req
    ) {
        String contents = req.getContents();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.commentService.updateCommentById(commentId, scheduleId, userId, contents));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> deleteComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.commentService.deleteCommentById(commentId, scheduleId));
    }
}
