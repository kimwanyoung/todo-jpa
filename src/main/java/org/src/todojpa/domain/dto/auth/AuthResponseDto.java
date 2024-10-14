package org.src.todojpa.domain.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponseDto {
    private String accessToken;

    public static AuthResponseDto from(String accessToken) {
        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .build();
    }
}
