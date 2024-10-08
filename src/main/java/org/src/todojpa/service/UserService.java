package org.src.todojpa.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.src.todojpa.domain.dto.UserCreateDto;
import org.src.todojpa.domain.dto.UserResponseDto;
import org.src.todojpa.domain.dto.UserUpdateDto;
import org.src.todojpa.domain.entity.User;
import org.src.todojpa.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto retrieveUserById(Long id) {
        User user = findUserById(id);

        return UserResponseDto.from(user);
    }

    public UserResponseDto createUser(UserCreateDto req) {
        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .build();

        User createdUser = this.userRepository.save(user);

        return UserResponseDto.from(createdUser);
    }

    @Transactional
    public UserResponseDto updateUserById(Long id, UserUpdateDto req) {
        User user = findUserById(id);

        user.update(req);

        return UserResponseDto.from(user);
    }

    public UserResponseDto deleteUserById(Long id) {
        User user = findUserById(id);

        this.userRepository.delete(user);

        return UserResponseDto.from(user);
    }

    private User findUserById(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
    }
}
