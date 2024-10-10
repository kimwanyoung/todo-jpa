package org.src.todojpa.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.src.todojpa.domain.dto.UserCreateDto;
import org.src.todojpa.domain.dto.UserResponseDto;
import org.src.todojpa.domain.dto.UserUpdateDto;
import org.src.todojpa.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> retrieveUser(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.userService.retrieveUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserCreateDto req) {
        String name = req.getName();
        String email = req.getEmail();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.userService.createUser(name, email));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateDto req
    ) {
        String name = req.getName();
        String email = req.getEmail();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.userService.updateUserById(id, name, email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDto> deleteUser(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.userService.deleteUserById(id));
    }
}
