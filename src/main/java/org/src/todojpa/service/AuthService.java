package org.src.todojpa.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.src.todojpa.config.PasswordEncoder;
import org.src.todojpa.domain.dto.auth.AuthResponseDto;
import org.src.todojpa.domain.entity.User;
import org.src.todojpa.jwt.JwtUtil;
import org.src.todojpa.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String signup(String name, String email, String password) {
        validateDuplicateEmail(email);

        String encodedPassword = passwordEncoder.encode(password);

        User user = User.builder()
                .name(name)
                .email(email)
                .password(encodedPassword)
                .build();

        User savedUser = this.userRepository.save(user);
        return this.jwtUtil.createToken(savedUser.getEmail());
    }

    public String login(String email, String password){
        User user = authenticateUser(email, password);

        return this.jwtUtil.createToken(user.getEmail());
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

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        return user;
    }
}