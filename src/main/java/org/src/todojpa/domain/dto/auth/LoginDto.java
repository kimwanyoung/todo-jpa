package org.src.todojpa.domain.dto.auth;

import lombok.Getter;

@Getter
public class LoginDto {
    private String email;
    private String password;
}
