package org.src.todojpa.controller;

import static org.src.todojpa.constants.AuthConstants.AUTHORIZATION_HEADER;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

        String token = this.authService.signup(username, email, password);
        assignJwtToCookie(token, response);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(AuthResponseDto.from(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            @RequestBody @Valid LoginDto req,
            HttpServletResponse response
    ) {
        String email = req.getEmail();
        String password = req.getPassword();

        String token = this.authService.login(email, password);
        assignJwtToCookie(token, response);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(AuthResponseDto.from(token));
    }

    private void assignJwtToCookie(String token, HttpServletResponse response) {
        token = URLEncoder.encode(token, StandardCharsets.UTF_8).replaceAll("\\+", "%20");

        Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token);
        cookie.setPath("/");

        response.addCookie(cookie);
    }
}
