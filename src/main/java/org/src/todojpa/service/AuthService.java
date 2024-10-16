package org.src.todojpa.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.src.todojpa.domain.entity.User;
import org.src.todojpa.domain.entity.UserRole;
import org.src.todojpa.jwt.JwtUtil;
import org.src.todojpa.repository.UserRepository;
import org.src.todojpa.security.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public String signup(String name, String email, String password) {
        validateDuplicateEmail(email);

        String encodedPassword = passwordEncoder.encode(password);

        // FIXME: 관리자 테스트 위한 코드, 추후 관리자 생성 방법이 생기면 제거 예정
        UserRole role = name.equals("admin") ? UserRole.ADMIN : UserRole.USER;

        User user = User.builder()
                .name(name)
                .email(email)
                .role(role)
                .password(encodedPassword)
                .build();

        User savedUser = this.userRepository.save(user);
        return this.jwtUtil.createToken(savedUser.getId(), role);
    }

    public String login(String email, String password) {
        User user = authenticateUser(email, password);

        return this.jwtUtil.createToken(user.getId(), user.getRole());
    }

    private void validateDuplicateEmail(String email) {
        this.userRepository.findByEmail(email).ifPresent((user) -> {
            throw new IllegalArgumentException("중복된 이메일이 존재합니다.");
        });
    }

    private User authenticateUser(String email, String password) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        return user;
    }
}