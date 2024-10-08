package org.src.todojpa.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.src.todojpa.domain.entity.User;

@Getter
@Setter
@Builder
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;

    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
