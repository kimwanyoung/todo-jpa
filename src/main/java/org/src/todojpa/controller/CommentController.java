package org.src.todojpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.src.todojpa.domain.dto.CommentResponseDto;
import org.src.todojpa.service.CommentService;

@RestController
@RequestMapping("/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> retrieveComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId
    ) {
        return ResponseEntity.ok(this.commentService.retrieveCommentById(scheduleId, commentId));
    }
}
