package org.src.todojpa.domain.dto;

import lombok.Getter;

@Getter
public class CommentCreateDto {
    private String username;
    private String contents;
}
