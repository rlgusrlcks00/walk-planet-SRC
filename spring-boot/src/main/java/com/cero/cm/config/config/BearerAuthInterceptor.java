package com.cero.cm.config.config;

import com.cero.cm.config.exception.UnauthorizedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BearerAuthInterceptor implements HandlerInterceptor {
    private AuthorizationExtractor authExtractor;
    private JwtTokenUtil jwtTokenProvider;

    public BearerAuthInterceptor(AuthorizationExtractor authExtractor, JwtTokenUtil jwtTokenProvider) {
        this.authExtractor = authExtractor;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) {
        System.out.println(">>> interceptor.preHandle 호출");
        String token = authExtractor.extract(request, "Bearer");
        System.out.println(">>> token = " + token);
        if (StringUtils.isEmpty(token)) {
            return true;
        }

        if (!jwtTokenProvider.validateToken(token)) {
            throw new UnauthorizedException("유효하지 않은 토큰");
        }

        String username = jwtTokenProvider.getSubject(token);
        request.setAttribute("username", username);
        return true;
    }
}
