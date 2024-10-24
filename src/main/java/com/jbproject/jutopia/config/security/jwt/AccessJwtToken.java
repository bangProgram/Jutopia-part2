package com.jbproject.jutopia.config.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jbproject.jutopia.auth.model.RoleBasedWhiteList;
import com.jbproject.jutopia.rest.model.result.RoleMenuResult;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/*
    토큰 (인증)

*/

public class AccessJwtToken implements Authentication {

    boolean authenticated = false;

    @Setter
    private AccessJwtToken.AccessJwtPrincipal accessJwtPrincipal;
    String role = "VISITOR";

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
    public AccessJwtToken.AccessJwtPrincipal getPrincipal() {
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

        Long id;
        String userId;
        String userEmail;
        String userName;
        String role;
    }

    @Getter
    public static class AccessJwtPrincipal {
        private final Long id;
        private final String userId;
        private final String userEmail;
        private final String userName;
        private final int age;
        private final String role;

        @Builder
        public AccessJwtPrincipal(
                Long id,
                String userId, String userEmail, String userName,
                int age, String role
        ){
            this.id = id;
            this.userId = userId;
            this.userEmail = userEmail;
            this.userName = userName;
            this.age = age;
            this.role = role;
        }
    }
}
