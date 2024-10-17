package org.src.todojpa.domain.dto.comment;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.src.todojpa.domain.dto.user.UserResponseDto;
import org.src.todojpa.domain.entity.Comment;

@Getter
@Setter
@Builder
public class CommentResponseDto {

    private Long id;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private UserResponseDto user;

    public static CommentResponseDto from(Comment comment) {
        UserResponseDto user = UserResponseDto.from(comment.getUser());
        return CommentResponseDto.builder()
                .id(comment.getId())
                .user(user)
                .contents(comment.getContents())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}
