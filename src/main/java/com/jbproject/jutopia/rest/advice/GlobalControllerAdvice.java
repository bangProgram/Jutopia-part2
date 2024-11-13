package com.jbproject.jutopia.rest.advice;

import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.config.security.model.Role;
import com.jbproject.jutopia.config.security.util.SecurityUtils;
import com.jbproject.jutopia.constant.CommonConstatns;
import com.jbproject.jutopia.rest.model.result.MenuResult;
import com.jbproject.jutopia.rest.model.result.RoleMenuResult;
import com.jbproject.jutopia.rest.repository.RoleMenuRepository;
import com.jbproject.jutopia.rest.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final RequestMatcher defaultPermitAllPath;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final MenuService menuService;

    private List<MenuResult> curMenuList = new ArrayList<>();

    @ModelAttribute("isAdmin")
    public boolean isAdmin() {
        Authentication authentication = securityContextHolderStrategy.getContext().getAuthentication();

        if (authentication instanceof AccessJwtToken accessJwtToken && accessJwtToken.isAuthenticated()) {
            return Role.getAccessRole(accessJwtToken.getPrincipal().getRole()).contains("ADMIN");
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

    @ModelAttribute("curPage")
    public String curPage(HttpServletRequest request) {
        String requestUrl = request.getRequestURI();
        return requestUrl.contains("/admin") ? "ADMIN" : "USER";
    }

    @ModelAttribute("topMenuList")
    public List<MenuResult> getTopMenuList(HttpServletRequest request) {

        if(defaultPermitAllPath.matches(request)){
            return curMenuList;
        }

        String requestUrl = request.getRequestURI();
        if(!requestUrl.contains("/admin")){
            curMenuList = menuService.getShowMenuList(CommonConstatns.MENU_ROLE_USER);
            return curMenuList;
        }else{
            return new ArrayList<>();
        }
    }
}
