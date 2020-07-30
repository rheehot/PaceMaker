package com.tatinic.issuetracker.service;

import com.tatinic.issuetracker.web.login.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService {

    private final GithubProperties githubProperties;
    private final JwtService jwtService;
    private final UserService userService;
    private final Integer EXPIRE_TIME = 60 * 60 * 6;

    public ResponseEntity<Void> loginProcess(String code, HttpServletResponse response) {
        GithubResponseDto githubResponseDto = requestAccessToken(code);
        log.info("Github AccessToken, TokenType, Scope Data : {}", githubResponseDto);
        GithubInformationOfUser githubInformationOfUser = requestUserInfo(githubResponseDto.getAccessToken());
        log.info("Github UserId : {}", githubInformationOfUser);

        userService.oauthUserSave(githubInformationOfUser);
        String jwt = jwtService.createJwt(githubInformationOfUser.getUserId());

        Cookie cookie = new Cookie(OauthEnum.USER_ID.getValue(), githubInformationOfUser.getUserId());
        cookie.setMaxAge(EXPIRE_TIME);
        response.addCookie(cookie);
        response.setHeader(OauthEnum.HEADER_LOCATION.getValue(), OauthEnum.MOBILE_REDIRECT_URL.getValue() + jwt);
        return new ResponseEntity<>(HttpStatus.FOUND);
    }

    private GithubResponseDto requestAccessToken(String code) {
        AccessTokenRequestBody accessTokenRequestBody = AccessTokenRequestBody.builder()
                .clientId(githubProperties.clientId)
                .clientSecret(githubProperties.clientSecret)
                .code(code)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.set(OauthEnum.HEADER_ACCEPT.getValue(), OauthEnum.HEADER_MEDIA_TYPE.getValue());
        HttpEntity<?> httpEntity = new HttpEntity<>(accessTokenRequestBody, headers);
        ResponseEntity<GithubResponseDto> responseEntity = new RestTemplate()
                .postForEntity(githubProperties.accessTokenUrl, httpEntity, GithubResponseDto.class);
        return responseEntity.getBody();
    }

    private GithubInformationOfUser requestUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<GithubInformationOfUser> responseEntity = new RestTemplate()
                .exchange(githubProperties.userEmailRequestUrl, HttpMethod.GET, httpEntity, GithubInformationOfUser.class);
        return githubUserValidation(responseEntity.getBody());
    }

    private GithubInformationOfUser githubUserValidation(GithubInformationOfUser githubInformationOfUser) {
        if (githubInformationOfUser.nameIsNull()) {
            githubInformationOfUser.fixName();
        }
        return githubInformationOfUser;
    }
}
