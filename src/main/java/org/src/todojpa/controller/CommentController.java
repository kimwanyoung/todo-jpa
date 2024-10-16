package org.src.todojpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.src.todojpa.domain.dto.comment.CommentCreateDto;
import org.src.todojpa.domain.dto.comment.CommentResponseDto;
import org.src.todojpa.domain.dto.comment.CommentUpdateDto;
import org.src.todojpa.domain.dto.user.VerifiedUserDto;
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
        Page<CommentResponseDto> commentResponseDtos = this.commentService.retrieveComments(scheduleId, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new PagedModel<>(commentResponseDtos));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> retrieveComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId
    ) {
        CommentResponseDto commentResponseDto = this.commentService.retrieveCommentById(commentId, scheduleId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentResponseDto);
    }

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long scheduleId,
            @RequestBody CommentCreateDto req,
            VerifiedUserDto verifiedUserDto
    ) {
        String contents = req.getContents();
        Long userId = verifiedUserDto.getUserId();

        CommentResponseDto commentResponseDto = this.commentService.createComment(contents, scheduleId, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentResponseDto);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentUpdateDto req,
            VerifiedUserDto verifiedUserDto
    ) {
        String contents = req.getContents();
        Long userId = verifiedUserDto.getUserId();

        CommentResponseDto commentResponseDto = this.commentService.updateCommentById(commentId, userId, contents);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentResponseDto);
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
