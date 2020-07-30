package com.tatinic.issuetracker.web.login;

import com.tatinic.issuetracker.service.JwtService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@NoArgsConstructor
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private JwtService jwtService;

    public LoginInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        final String jwtToken = request.getHeader(OauthEnum.AUTHORIZATION.getValue());
        log.info("token >> {}", jwtToken);
        String userId = jwtService.getUserId(jwtToken);

        if (!jwtService.isValidToken(jwtToken)) {
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            return false;
        }
        request.setAttribute("userId", userId);
        return true;
    }
}
