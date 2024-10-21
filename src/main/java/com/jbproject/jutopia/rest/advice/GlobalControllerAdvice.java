package com.jbproject.jutopia.rest.advice;

import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.config.security.model.Role;
import com.jbproject.jutopia.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    @ModelAttribute("isAdmin")
    public boolean isAdmin() {
        Authentication authentication = securityContextHolderStrategy.getContext().getAuthentication();

        if (authentication instanceof AccessJwtToken accessJwtToken && accessJwtToken.isAuthenticated()) {
            return Role.ADMIN.getAccessRole().contains(accessJwtToken.getPrincipal().getRole());
        }

        return false;
    }

    @ModelAttribute("isRole")
    public String isRole() {
        Authentication authentication = securityContextHolderStrategy.getContext().getAuthentication();

        if (authentication instanceof AccessJwtToken accessJwtToken && accessJwtToken.isAuthenticated()) {
            return accessJwtToken.getPrincipal().getRole();
        }

        return "VISITOR";
    }
}
