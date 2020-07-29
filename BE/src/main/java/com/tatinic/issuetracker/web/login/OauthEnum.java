package com.tatinic.issuetracker.web.login;

import lombok.Getter;

@Getter
public enum OauthEnum {
    USER_ID("userId"),  HEADER_LOCATION("Location"),
    MOBILE_REDIRECT_URL("issue04://?token="),
    HEADER_ACCEPT("Accept"), HEADER_MEDIA_TYPE("application/json"),
    AUTHORIZATION("Authorization"),
    OAUTH_URL_SERVER("https://github.com/login/oauth/authorize?client_id=bdd909bfff2137535182&redirect_uri=http://localhost:8080/callback&scope=user"),
    SECRET_KEY("issueTracker04"),
    TYP("typ"), TYP_VALUE("JWT"), ALG("HS256");

    private String value;

    OauthEnum(String value) {
        this.value = value;
    }
}
