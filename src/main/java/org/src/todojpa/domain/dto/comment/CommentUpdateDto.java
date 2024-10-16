package org.src.todojpa.domain.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentUpdateDto {

    @NotBlank(message = "내용은 필수 입니다.")
    private String contents;
}
