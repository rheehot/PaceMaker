package com.tatinic.issuetracker.web.login;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("github")
@Getter
@Setter
public class GithubProperties {

    public String accessTokenUrl;
    public String clientId;
    public String clientSecret;
    public String redirectUrl;
    public String userEmailRequestUrl;
}
