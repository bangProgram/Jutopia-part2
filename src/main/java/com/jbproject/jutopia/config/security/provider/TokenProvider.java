package com.jbproject.jutopia.config.security.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.config.security.constant.JwtTokenConstants;
import com.jbproject.jutopia.config.security.jwt.AccessJwtPrincipal;
import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.config.security.jwt.JwtTokenInfo;
import com.jbproject.jutopia.config.security.jwt.RefreshJwtToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class TokenProvider {

    @Value("${jutopia.jwt.secret}")
    private String accessSecret;
    @Value("${jutopia.jwt.access.duration}")
    private Long accessTokenExpired;
    @Value("${jutopia.jwt.refresh.duration}")
    private Long refreshTokenExpired;

    private final ObjectMapper objectMapper = new ObjectMapper();


    private SecretKey getSecretKey(){
        byte[] keyBytes = Decoders.BASE64.decode(accessSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public AccessJwtToken.CustomClaims getCustomClaims(String accessToken){
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build();
        Claims body = parser.parseClaimsJws(accessToken).getBody();
        return objectMapper.convertValue(body, AccessJwtToken.CustomClaims.class);
    }


    public String getRole(String accessToken) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build();
        Claims body = parser.parseClaimsJws(accessToken).getBody();
        AccessJwtToken.CustomClaims custom =  objectMapper.convertValue(body, AccessJwtToken.CustomClaims.class);

        return custom.getRole();
    }

    public String getUserEmail(String accessToken) {
        return Jwts.parser()
                .setSigningKey(getSecretKey())
                .parseClaimsJws(accessToken)
                .getBody()
                .getSubject();
    }

    public JwtTokenInfo generateToken(AccessJwtToken authentication){
        AccessJwtPrincipal principal = authentication.getPrincipal();

        AccessJwtToken.CustomClaims accessClaims = AccessJwtToken.CustomClaims.builder()
                .userId(principal.getUserId())
                .email(principal.getUserEmail())
                .role(principal.getRole()
                )
                .build();

        String accessToken = Jwts.builder()
                .setSubject(principal.getUserEmail())
                .setClaims(objectMapper.convertValue(accessClaims, Map.class))
                .setExpiration(getExpirationDate(JwtTokenConstants.ACCESS))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact()
                ;

        RefreshJwtToken.CustomClaims refreshCliams = RefreshJwtToken.CustomClaims.builder()
                .userId(principal.getUserId())
                .role(principal.getRole())
                .build();

        String refreshToken = Jwts.builder()
                .setExpiration(getExpirationDate(JwtTokenConstants.REFRESH))
                .setClaims(objectMapper.convertValue(refreshCliams, Map.class))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact()
                ;

        return JwtTokenInfo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String refreshToken(String accessToken) {
        SecretKey secretKey = getSecretKey();
        JwtParser parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
        Claims body = parser.parseClaimsJws(accessToken).getBody();

        JwtBuilder builder = Jwts.builder()
                .setClaims(body)
                .setExpiration(getExpirationDate(JwtTokenConstants.REFRESH))
                .setIssuedAt(new Date())
                .signWith(secretKey);

        return builder.compact();
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    public Date getExpirationDate(String type){
        long validity = 0;

        if(type.equals(JwtTokenConstants.ACCESS)){
            validity = Duration.ofSeconds(accessTokenExpired).toMillis();
        }else{
            validity = Duration.ofSeconds(refreshTokenExpired).toMillis();
        }

        Date createdTime = new Date();
        return new Date(createdTime.getTime() + validity);
    }


    public long getExpirationTime(String type){
        long validity = 0;

        if(type.equals(JwtTokenConstants.ACCESS)){
            validity = Duration.ofSeconds(accessTokenExpired).toMillis();
        }else{
            validity = Duration.ofSeconds(refreshTokenExpired).toMillis();
        }

        return validity;
    }

    public Date getRefreshExpirationTime(){
        long validity = Duration.ofSeconds(refreshTokenExpired).toMillis();
        Date createdTime = new Date();
        return new Date(createdTime.getTime() + validity);
    }

}
