package com.tatinic.issuetracker.web.controller;

import com.tatinic.issuetracker.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/callback")
    public ResponseEntity<Void> oauthCallback(@Param("code") String code, HttpServletResponse response) {
        return loginService.loginProcess(code, response);
    }
}
