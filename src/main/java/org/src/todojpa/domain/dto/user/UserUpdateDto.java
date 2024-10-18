package org.src.todojpa.domain.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserUpdateDto {

    @NotBlank
    private String name;
    @NotBlank
    private String email;
}
