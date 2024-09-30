package com.jbproject.jutopia.config.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Getter
@RequiredArgsConstructor
public class RefreshJwtToken implements Authentication {

    private boolean authenticated = false;

    @Setter
    private RefreshJwtToken.RefreshJwtPrincipal refreshJwtPrincipal;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
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
        return refreshJwtPrincipal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        this.authenticated = authenticated;
    }

    @Override
    public String getName() {
        return null;
    }


    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CustomClaims {
        Long id;
        String role;
    }

    @Getter
    public static class RefreshJwtPrincipal {
        private final Long id;
        private final String role;

        @Builder
        public RefreshJwtPrincipal(
                Long id, String role
        ){
            this.id = id;
            this.role = role;
        }
    }
}
