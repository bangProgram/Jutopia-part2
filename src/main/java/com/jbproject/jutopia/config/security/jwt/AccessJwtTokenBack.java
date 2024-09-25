package com.jbproject.jutopia.config.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/*
    토큰 (인증)

*/

public class AccessJwtTokenBack implements Authentication {

    boolean authenticated = false;
    AccessJwtPrincipal accessJwtPrincipal;
    String role = "VISITOR";

    public AccessJwtTokenBack(AccessJwtPrincipal principal){
        this.accessJwtPrincipal = principal;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return accessJwtPrincipal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return null;
    }

    public void setRole(String role){
        this.role = role;
    }

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
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccessJwtPrincipal {
        private String userEmail;
        private String userName;
        private int age;
        private String socialType;
        private String socialId;
        private String role;
    }
}
