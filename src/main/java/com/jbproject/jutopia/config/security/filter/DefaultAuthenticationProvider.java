package com.jbproject.jutopia.config.security.filter;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class DefaultAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("JB : CustomAuthenticationProvider 동작 진행");
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return DefaultAuthenticationProvider.class.isAssignableFrom(authentication);
    }
}
