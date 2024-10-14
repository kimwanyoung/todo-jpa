package org.src.todojpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.src.todojpa.domain.dto.auth.RegisterDto;
import org.src.todojpa.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto req) {
        String token = this.authService.signup(req.getName(), req.getEmail(), req.getPassword());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(token);
    }

}
