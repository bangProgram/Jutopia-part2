package com.jbproject.jutopia.config.security.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccessJwtPrincipal {
    private final String userId;
    private final String userEmail;
    private final String userName;
    private final String role;

    @Builder
    public AccessJwtPrincipal(
        String userId, String userEmail, String userName,
        int age, String role, String refreshToken
    ){
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.role = role;
    }
}
