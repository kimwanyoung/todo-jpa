package org.src.todojpa.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.src.todojpa.domain.dto.auth.AuthResponseDto;
import org.src.todojpa.domain.dto.auth.LoginDto;
import org.src.todojpa.domain.dto.auth.RegisterDto;
import org.src.todojpa.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(
            @RequestBody @Valid RegisterDto req,
            HttpServletResponse response
    ) {
        String username = req.getName();
        String email = req.getEmail();
        String password = req.getPassword();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.authService.signup(username, email, password, response));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            @RequestBody @Valid LoginDto req,
            HttpServletResponse response
    ) {
        String email = req.getEmail();
        String password = req.getPassword();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.authService.login(email, password, response));
    }

}
