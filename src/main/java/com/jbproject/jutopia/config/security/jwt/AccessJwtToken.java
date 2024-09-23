package com.jbproject.jutopia.config.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AccessJwtToken implements Authentication {

    boolean authenticated = false;
    private final AccessJwtPrincipal accessJwtPrincipal;
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

    public AccessJwtToken(AccessJwtPrincipal principal){
        this.accessJwtPrincipal = principal;
    }

    public void setRole(String role){
        this.role = role;
    }
}
