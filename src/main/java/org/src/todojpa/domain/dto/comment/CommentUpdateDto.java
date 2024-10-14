package org.src.todojpa.domain.dto.comment;

import lombok.Getter;

@Getter
public class CommentUpdateDto {
    private String contents;
    private Long userId;
}
