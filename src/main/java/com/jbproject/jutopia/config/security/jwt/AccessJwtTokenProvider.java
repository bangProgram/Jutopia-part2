package com.jbproject.jutopia.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.config.security.jwt.properties.AccessJwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class AccessJwtTokenProvider {

    private final AccessJwtProperties accessJwtProperties;
    private final ObjectMapper objectMapper;

    private SecretKey getSecretKey(){
        byte[] keyBytes = Decoders.BASE64.decode(accessJwtProperties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims getClaims(String token){
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build();
        return parser.parseClaimsJws(token).getBody();
    }

    public AccessJwtFacade.CustomClaims getCustomClaims(String token){
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build();
        Claims body = parser.parseClaimsJws(token).getBody();
        return objectMapper.convertValue(body, AccessJwtFacade.CustomClaims.class);
    }


    public String getRole(String token) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build();
        Claims body = parser.parseClaimsJws(token).getBody();
        AccessJwtFacade.CustomClaims custom =  objectMapper.convertValue(body, AccessJwtFacade.CustomClaims.class);

        return custom.getRole();
    }

    public String getUserEmail(String token) {
        return Jwts.parser()
                .setSigningKey(getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String createCustomToken(AccessJwtFacade.CustomClaims customClaims){
        JwtBuilder builder = Jwts.builder()
                .setSubject(customClaims.email)
                .setClaims(this.objectMapper.convertValue(customClaims, Map.class))
                .setExpiration(getExpirationTime())
                .signWith(getSecretKey());
        return builder.compact();
    }

    public Date getExpirationTime(){
        long validity = Duration.ofSeconds(Long.parseLong(accessJwtProperties.getDuration())).toMillis();
        Date createdTime = new Date();
        return new Date(createdTime.getTime() + validity);
    }
}
