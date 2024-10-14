package org.src.todojpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.src.todojpa.domain.dto.auth.AuthResponseDto;
import org.src.todojpa.domain.dto.auth.RegisterRequestDto;
import org.src.todojpa.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequestDto req) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.authService.signup(req.getName(), req.getEmail(), req.getPassword()));
    }

}
