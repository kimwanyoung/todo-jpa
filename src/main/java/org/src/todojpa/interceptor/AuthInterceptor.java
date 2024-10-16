package org.src.todojpa.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.src.todojpa.jwt.JwtUtil;

@Slf4j(topic = "Interceptor Logger")
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String token = this.jwtUtil.extractTokenFromRequest(request);
            this.jwtUtil.validateToken(token);
            return true;
        } catch (Exception e) {
            log.error("[Exception] : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}