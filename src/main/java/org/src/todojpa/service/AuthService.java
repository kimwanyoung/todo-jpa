package org.src.todojpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    public AuthResponseDto signup(String name, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);

        this.userRepository.findByEmail(email).ifPresent((user) -> {
            throw new IllegalArgumentException("중복된 이메일이 존재합니다.");
        });

        User user = User.builder()
                .name(name)
                .email(email)
                .password(encodedPassword)
                .build();

        this.userRepository.save(user);

        String token = this.jwtUtil.createToken(name);
        return AuthResponseDto.from(token);
    }

    public AuthResponseDto login(String email, String password){
        User user = this.userRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = this.jwtUtil.createToken(user.getName());
        return AuthResponseDto.from(token);
    }
}