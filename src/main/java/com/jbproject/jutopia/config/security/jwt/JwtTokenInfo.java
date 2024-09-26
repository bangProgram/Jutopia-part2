package com.jbproject.jutopia.config.security.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtTokenInfo {

    private final String accessToken;
    private final String refreshToken;

    @Builder
    public JwtTokenInfo(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
