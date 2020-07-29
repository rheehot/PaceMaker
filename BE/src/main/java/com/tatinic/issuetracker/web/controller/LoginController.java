package com.tatinic.issuetracker.web.controller;

import com.tatinic.issuetracker.service.JwtService;
import com.tatinic.issuetracker.service.LoginService;
import com.tatinic.issuetracker.service.UserService;
import com.tatinic.issuetracker.web.login.GithubInformationOfUser;
import com.tatinic.issuetracker.web.login.GithubResponseDto;
import com.tatinic.issuetracker.web.login.OauthEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final JwtService jwtService;
    private final UserService userService;
    private final Integer EXPIRE_TIME = 60 * 60 * 6;

    @GetMapping("/callback")
    public ResponseEntity<Void> oauthCallback(@Param("code") String code, HttpServletResponse response) {
        GithubResponseDto github = loginService.requestAccessToken(code);
        log.info("Github AccessToken, TokenType, Scope Data : {}", github);
        GithubInformationOfUser githubUser = loginService.requestUserInfo(github.getAccessToken());
        log.info("Github UserId : {}", githubUser);

        userService.save(githubUser);
        String jwt = jwtService.createJwt(githubUser.getUserId());

        Cookie cookie = new Cookie(OauthEnum.USER_ID.getValue(), githubUser.getUserId());
        cookie.setMaxAge(EXPIRE_TIME);
        response.addCookie(cookie);
        response.setHeader(OauthEnum.HEADER_LOCATION.getValue(), OauthEnum.MOBILE_REDIRECT_URL.getValue() + jwt);
        return new ResponseEntity<>(HttpStatus.FOUND);
    }

}
