package org.src.todojpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.src.todojpa.domain.dto.user.UserResponseDto;
import org.src.todojpa.domain.dto.user.UserUpdateDto;
import org.src.todojpa.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> retrieveUser(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.userService.retrieveUserById(id));
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
