package org.src.todojpa.domain.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VerifiedUserDto {
    private final Long userId;
}
