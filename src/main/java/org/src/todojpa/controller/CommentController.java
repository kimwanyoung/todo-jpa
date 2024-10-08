package org.src.todojpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.src.todojpa.domain.dto.CommentCreateDto;
import org.src.todojpa.domain.dto.CommentResponseDto;
import org.src.todojpa.service.CommentService;

@RestController
@RequestMapping("/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<PagedModel<CommentResponseDto>> retrieveComments(@PathVariable Long scheduleId, Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new PagedModel<>(this.commentService.retrieveComments(scheduleId, pageable)));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> retrieveComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId
    ) {
        return ResponseEntity.ok(this.commentService.retrieveCommentById(scheduleId, commentId));
    }

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long scheduleId,
            @RequestBody CommentCreateDto req
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.commentService.createComment(scheduleId, req));
    }
}
