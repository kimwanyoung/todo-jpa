package org.src.todojpa.controller;

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
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateDto req) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.userService.createUser(req));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateDto req
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.userService.updateUserById(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDto> deleteUser(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.userService.deleteUserById(id));
    }
}
