package com.jbproject.jutopia.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.config.security.jwt.properties.AccessJwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

/*
    토큰제공자 :
    토큰 발급, 토큰 재발급, 토큰 정보 호출
*/

@RequiredArgsConstructor
public class AccessJwtTokenProvider {

    private final AccessJwtProperties accessJwtProperties;
    private final ObjectMapper objectMapper;

    private SecretKey getSecretKey(){
        System.out.println("JB getSecretKey "+accessJwtProperties.getSecret());
        System.out.println("JB getSecretKey "+ Encoders.BASE64.encode(accessJwtProperties.getSecret().getBytes()));
        ;
        byte[] keyBytes = Decoders.BASE64.decode(Encoders.BASE64.encode(accessJwtProperties.getSecret().getBytes()));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims getClaims(String token){
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build();
        return parser.parseClaimsJws(token).getBody();
    }

    public AccessJwtTokenBack.CustomClaims getCustomClaims(String token){
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build();
        Claims body = parser.parseClaimsJws(token).getBody();
        return objectMapper.convertValue(body, AccessJwtTokenBack.CustomClaims.class);
    }


    public String getRole(String token) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build();
        Claims body = parser.parseClaimsJws(token).getBody();
        AccessJwtTokenBack.CustomClaims custom =  objectMapper.convertValue(body, AccessJwtTokenBack.CustomClaims.class);

        return custom.getRole();
    }

    public String getUserEmail(String token) {
        return Jwts.parser()
                .setSigningKey(getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String createCustomToken(AccessJwtTokenBack.CustomClaims customClaims){
        JwtBuilder builder = Jwts.builder()
                .setSubject(customClaims.email)
                .setClaims(this.objectMapper.convertValue(customClaims, Map.class))
                .setExpiration(getExpirationTime())
                .signWith(getSecretKey());

        return builder.compact();
    }

    public String refreshToken(String accessToken) {
        SecretKey secretKey = getSecretKey();
        JwtParser parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
        Claims body = parser.parseClaimsJws(accessToken).getBody();

        JwtBuilder builder = Jwts.builder()
                .setClaims(body)
                .setExpiration(getExpirationTime())
                .setIssuedAt(new Date())
                .signWith(secretKey);

        return builder.compact();
    }

    public Date getExpirationTime(){
        long validity = Duration.ofSeconds(Long.parseLong(accessJwtProperties.getDuration())).toMillis();
        Date createdTime = new Date();
        return new Date(createdTime.getTime() + validity);
    }
}
