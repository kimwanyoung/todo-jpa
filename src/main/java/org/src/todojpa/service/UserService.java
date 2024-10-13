package org.src.todojpa.service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.src.todojpa.config.PasswordEncoder;
import org.src.todojpa.domain.dto.UserResponseDto;
import org.src.todojpa.domain.entity.User;
import org.src.todojpa.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto retrieveUserById(Long id) {
        User user = findUserById(id);

        return UserResponseDto.from(user);
    }

    public UserResponseDto signup(String name, String email, String password) {
        this.userRepository.findByEmail(email).ifPresent((user) -> {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        });

        String encodedPassword = this.passwordEncoder.encode(password);

        User user = User.builder()
                .name(name)
                .email(email)
                .password(encodedPassword)
                .build();

        User createdUser = this.userRepository.save(user);

        return UserResponseDto.from(createdUser);
    }

    public void login(String email, String password, HttpServletResponse res) {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일 사용자는 존재하지 않습니다."));

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalStateException("올바르지 않은 비밀번호 입니다.");
        }

        // TODO: JWT 반환 필요
    }

    @Transactional
    public UserResponseDto updateUserById(Long id, String name, String email) {
        User user = findUserById(id);

        user.update(name, email);

        return UserResponseDto.from(user);
    }

    public UserResponseDto deleteUserById(Long id) {
        User user = findUserById(id);

        this.userRepository.delete(user);

        return UserResponseDto.from(user);
    }

    public User findUserById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("존재하지 않는 유저입니다.")
        );
    }
}
