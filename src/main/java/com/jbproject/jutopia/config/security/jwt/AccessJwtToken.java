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

public class AccessJwtToken implements Authentication {

    boolean authenticated = false;
//    private final AccessJwtPrincipal accessJwtPrincipal;

    AccessJwtPrincipal accessJwtPrincipal;
    String role = "VISITOR";

//    public AccessJwtToken(AccessJwtPrincipal principal) {
//        accessJwtPrincipal = principal;
//    }

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
    public AccessJwtPrincipal getPrincipal() {
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

    public void setAccessJwtPrincipal(AccessJwtPrincipal principal){
        this.accessJwtPrincipal = principal;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CustomClaims {

        String userId;
        String email;
        String role;
    }

}
