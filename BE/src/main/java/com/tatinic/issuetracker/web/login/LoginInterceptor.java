package com.tatinic.issuetracker.web.login;

import com.tatinic.issuetracker.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        final String token = request.getHeader(OauthEnum.AUTHORIZATION.getValue());
        log.info("token >> {}", token);

        return jwtService.isValidToken(token);
    }
}
