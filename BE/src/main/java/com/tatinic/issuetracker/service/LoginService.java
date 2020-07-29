package com.tatinic.issuetracker.service;

import com.tatinic.issuetracker.web.login.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final GithubProperties githubProperties;

    public GithubResponseDto requestAccessToken(String code) {
        AccessTokenRequestBody accessTokenRequestBody = AccessTokenRequestBody.builder()
                .clientId(githubProperties.clientId)
                .clientSecret(githubProperties.clientSecret)
                .code(code)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.set(OauthEnum.HEADER_ACCEPT.getValue(), OauthEnum.HEADER_MEDIA_TYPE.getValue());
        HttpEntity<?> httpEntity = new HttpEntity<>(accessTokenRequestBody, headers);
        ResponseEntity<GithubResponseDto> responseEntity = new RestTemplate().postForEntity(githubProperties.accessTokenUrl, httpEntity, GithubResponseDto.class);
        return responseEntity.getBody();
    }

    public GithubInformationOfUser requestUserInfo(String accessToken) {
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
