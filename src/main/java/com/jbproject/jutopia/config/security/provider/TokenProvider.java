package com.jbproject.jutopia.config.security.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.config.security.constant.JwtTokenConstants;
import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.config.security.jwt.JwtTokenInfo;
import com.jbproject.jutopia.config.security.jwt.RefreshJwtToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class TokenProvider {

    @Value("${jutopia.jwt.access.secret}")
    private String accessSecret;
    @Value("${jutopia.jwt.refresh.secret}")
    private String refreshSecret;
    @Value("${jutopia.jwt.access.duration}")
    private Long accessTokenExpired;
    @Value("${jutopia.jwt.refresh.duration}")
    private Long refreshTokenExpired;

    private final ObjectMapper objectMapper = new ObjectMapper();


    private SecretKey getSecretKey(String type){
        byte[] keyBytes;
        if(type.equals(JwtTokenConstants.ACCESS.getName())){
            keyBytes = Decoders.BASE64.decode(accessSecret);
        }else{
            keyBytes = Decoders.BASE64.decode(refreshSecret);
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims getClaims(String token, String type){
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(getSecretKey(type))
                .build();
        return parser.parseClaimsJws(token).getBody();
    }


    public String getRole(String accessToken) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(getSecretKey(JwtTokenConstants.ACCESS.getName()))
                .build();
        Claims body = parser.parseClaimsJws(accessToken).getBody();
        AccessJwtToken.CustomClaims custom =  objectMapper.convertValue(body, AccessJwtToken.CustomClaims.class);

        return custom.getRole();
    }

    public String getUserEmail(String accessToken) {
        return Jwts.parser()
                .setSigningKey(getSecretKey(JwtTokenConstants.ACCESS.getName()))
                .parseClaimsJws(accessToken)
                .getBody()
                .getSubject();
    }

    public JwtTokenInfo generateToken(AccessJwtToken.CustomClaims customClaims){
        String accessToken = Jwts.builder()
                .setSubject(customClaims.getId().toString())
                .setClaims(objectMapper.convertValue(customClaims, Map.class))
                .setExpiration(getExpirationDate(JwtTokenConstants.ACCESS.getName()))
                .signWith(getSecretKey(JwtTokenConstants.ACCESS.getName()), SignatureAlgorithm.HS256)
                .compact()
                ;

        RefreshJwtToken.CustomClaims refreshCliams = RefreshJwtToken.CustomClaims.builder()
                .id(customClaims.getId())
                .role(customClaims.getRole())
                .build();

        String refreshToken = Jwts.builder()
                .setExpiration(getExpirationDate(JwtTokenConstants.REFRESH.getName()))
                .setClaims(objectMapper.convertValue(refreshCliams, Map.class))
                .signWith(getSecretKey(JwtTokenConstants.REFRESH.getName()), SignatureAlgorithm.HS256)
                .compact()
                ;

        return JwtTokenInfo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token, String type) {
        try {
            SecretKey secret;
            if(type.equals(JwtTokenConstants.ACCESS.getName())){
                secret = getSecretKey(JwtTokenConstants.ACCESS.getName());
            }else{
                secret = getSecretKey(JwtTokenConstants.REFRESH.getName());
            }

            Jwts.parserBuilder()
                    .setSigningKey(secret)
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

        if(type.equals(JwtTokenConstants.ACCESS.getName())){
            validity = Duration.ofSeconds(accessTokenExpired).toMillis();
        }else{
            validity = Duration.ofSeconds(refreshTokenExpired).toMillis();
        }

        Date createdTime = new Date();
        return new Date(createdTime.getTime() + validity);
    }


    public long getExpirationTime(String type){
        long validity = 0;

        if(type.equals(JwtTokenConstants.ACCESS.getName())){
            validity = Duration.ofSeconds(accessTokenExpired).toSeconds();
        }else{
            validity = Duration.ofSeconds(refreshTokenExpired).toSeconds();
        }

        return validity;
    }

    public Date getRefreshExpirationTime(){
        long validity = Duration.ofSeconds(refreshTokenExpired).toMillis();
        Date createdTime = new Date();
        return new Date(createdTime.getTime() + validity);
    }

    public AccessJwtToken getAccessAuthentication(String token){
        Claims claims =  getClaims(token, JwtTokenConstants.ACCESS.getName());
        AccessJwtToken.CustomClaims customClaims = objectMapper.convertValue(claims, AccessJwtToken.CustomClaims.class);

        AccessJwtToken accessJwtToken = new AccessJwtToken();
        accessJwtToken.setAccessJwtPrincipal(
                AccessJwtToken.AccessJwtPrincipal.builder()
                        .id(customClaims.getId())
                        .userId(customClaims.getUserId())
                        .userEmail(customClaims.getUserEmail())
                        .userName(customClaims.getUserName())
                        .role(customClaims.getRole())
                        .build()
        );

        return accessJwtToken;
    }

    public RefreshJwtToken getRefreshAuthentication(String token){
        Claims claims =  getClaims(token, JwtTokenConstants.REFRESH.getName());
        RefreshJwtToken.CustomClaims customClaims = objectMapper.convertValue(claims, RefreshJwtToken.CustomClaims.class);

        RefreshJwtToken refreshJwtToken = new RefreshJwtToken();
        refreshJwtToken.setRefreshJwtPrincipal(
                RefreshJwtToken.RefreshJwtPrincipal.builder()
                        .id(customClaims.getId())
                        .role(customClaims.getRole())
                        .build()
        );

        return refreshJwtToken;
    }

}
