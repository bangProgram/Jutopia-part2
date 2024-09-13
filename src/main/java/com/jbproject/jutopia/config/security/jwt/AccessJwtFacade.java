package com.jbproject.jutopia.config.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jbproject.jutopia.config.security.jwt.properties.AccessJwtProperties;
import com.jbproject.jutopia.config.security.jwt.properties.RefreshJwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.*;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
public class AccessJwtFacade {
    private final AccessJwtTokenProvider accessJwtTokenProvider;

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CustomClaims {

        @Builder
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class SocialStatus {
            String socialType;
            String socialId;
        }

        String email;
        String role;
        SocialStatus socialStatus;
    }
    public Claims getClaims(String token){
        return accessJwtTokenProvider.getClaims(token);
    }

    public AccessJwtFacade.CustomClaims getCustomClaims(String token){
        return accessJwtTokenProvider.getCustomClaims(token);
    }


    public String getRole(String token) {
        return accessJwtTokenProvider.getRole(token);
    }

    public String getUserEmail(String token) {
        return accessJwtTokenProvider.getUserEmail(token);
    }

    public String createToken(AccessJwtFacade.CustomClaims customClaims){
        return accessJwtTokenProvider.createCustomToken(customClaims);
    }

}
