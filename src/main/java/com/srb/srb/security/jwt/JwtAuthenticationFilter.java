package com.srb.srb.security.jwt;

import com.srb.srb.domain.entity.Token;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    /**
     * 토큰 유효성 검사 및 존재여부 검사 필터
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = tokenProvider.resolveAccessToken(request);
        String path = request.getServletPath();

        if (token != null) {
            String userId = tokenProvider.getPayloadByToken(token).get("sub");
            Token refreshToken = tokenProvider.getRefreshToken(userId);

            if (refreshToken != null && path.startsWith("/api/reissue/refresh")) {
                filterChain.doFilter(request, response);
            } else {
                if (tokenProvider.refreshTokenValidationToken(refreshToken.getValue())) {
                    if (path.startsWith("/api/reissue/access")) {
                        filterChain.doFilter(request, response);
                    } else if (tokenProvider.accessTokenValidationToken(token)) {
                        setAuthentication(token);
                        filterChain.doFilter(request, response);
                    }
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    /**
     * SecurityContextHolder 에 Authentication 저장
     * @param token
     */
    private void setAuthentication(String token) {
        Authentication authentication = tokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
