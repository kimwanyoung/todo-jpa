package org.src.todojpa.domain.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import static org.src.todojpa.constants.GlobalConstants.EMAIL_REGEXP;

@Getter
public class UserCreateDto {
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수입니다.")
    @Pattern(regexp = EMAIL_REGEXP, message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
