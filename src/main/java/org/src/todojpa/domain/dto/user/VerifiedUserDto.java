package org.src.todojpa.domain.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.src.todojpa.domain.entity.UserRole;

@Getter
@RequiredArgsConstructor
public class VerifiedUserDto {
    private final Long userId;
    private final UserRole role;
}
