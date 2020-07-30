package com.tatinic.issuetracker.service;

import com.tatinic.issuetracker.domain.account.LoginType;
import com.tatinic.issuetracker.exception.InvalidTokenException;
import com.tatinic.issuetracker.web.login.OauthEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private final String GUEST = "guest";
    private final SignatureAlgorithm SIGNATUREALGORITHM = SignatureAlgorithm.HS256;
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;


    public String createJwt(String userId) {
        Map<String, Object> header = new HashMap<>();
        header.put(OauthEnum.TYP.getValue(), OauthEnum.TYP_VALUE.getValue());
        header.put(OauthEnum.ALG.getValue(), OauthEnum.ALG.getValue());

        Map<String, Object> payload = new HashMap<>();
        payload.put(OauthEnum.AUTHORIZATION.getValue(), userId);

        return Jwts.builder()
                .setHeader(header)
                .setClaims(payload)
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SIGNATUREALGORITHM, OauthEnum.SECRET_KEY.getValue())
                .compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(OauthEnum.SECRET_KEY.getValue()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserId(String jwtToken) {
        if (jwtToken != null) {
            Jws<Claims> claims = Jwts.parser().setSigningKey(OauthEnum.SECRET_KEY.getValue()).parseClaimsJws(jwtToken);
            return claims.getBody().getSubject();
        }
        return LoginType.GUEST.getValue();
    }
}
