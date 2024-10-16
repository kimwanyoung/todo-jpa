package org.src.todojpa.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.src.todojpa.jwt.JwtUtil;

import java.io.IOException;

@Slf4j(topic = "AuthFilter")
@RequiredArgsConstructor
@Component
@Order(1)
public class AuthFilter implements Filter {

    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();

        if (isAuthNotRequired(url)) {
            chain.doFilter(request, response);
            return;
        }

        String email = this.jwtUtil.getEmailFromRequest(httpServletRequest);
        request.setAttribute("email", email);
        chain.doFilter(request, response);
    }

    private boolean isAuthNotRequired(String url) {
        return StringUtils.hasText(url) && (url.startsWith("/api/auth") || url.startsWith("/css") || url.startsWith("/js"));
    }
}
