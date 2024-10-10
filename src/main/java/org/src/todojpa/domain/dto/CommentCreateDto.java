package org.src.todojpa.domain.dto;

import lombok.Getter;

@Getter
public class CommentCreateDto {
    private Long userId;
    private String contents;
}
