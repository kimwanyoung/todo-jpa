package org.src.todojpa.jwt;

import static org.src.todojpa.constants.AuthConstants.AUTHORITY;
import static org.src.todojpa.constants.AuthConstants.AUTHORIZATION_HEADER;
import static org.src.todojpa.constants.AuthConstants.BEARER_PREFIX;
import static org.src.todojpa.constants.AuthConstants.JWT_SECRET_KEY;
import static org.src.todojpa.constants.AuthConstants.SIGNATURE_ALGORITHM;
import static org.src.todojpa.constants.AuthConstants.TOKEN_TIME;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.IllformedLocaleException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import org.src.todojpa.domain.dto.user.VerifiedUserDto;
import org.src.todojpa.domain.entity.UserRole;

@Slf4j(topic = "JWT Logger")
@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value(JWT_SECRET_KEY)
    private String secretKey;
    private Key key;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String extractTokenFromRequest(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(AUTHORIZATION_HEADER)) {
                    String bearerToken = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                    return substringToken(bearerToken);
                }
            }
        }
        return null;
    }


    public VerifiedUserDto extractVerifiedUserFromToken(String token) {
        if (!StringUtils.hasText(token)) {
            throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
        }

        Claims userInfo = getUserInfoFromToken(token);
        Long userId = Long.parseLong(userInfo.getSubject());
        UserRole role = UserRole.from(userInfo.get(AUTHORITY, String.class));

        return new VerifiedUserDto(userId, role);
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            throw new SecurityException();
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            throw new UnsupportedJwtException(e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            throw new IllformedLocaleException("잘못된 JWT 토큰 입니다.");
        }
    }

    public String createToken(Long userId, UserRole role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(userId + "")
                        .claim(AUTHORITY, role)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, SIGNATURE_ALGORITHM)
                        .compact();
    }

    private String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        log.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }

    private Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
