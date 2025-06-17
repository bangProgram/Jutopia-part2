package com.jbproject.jutopia.rest.advice;

import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.config.security.model.Role;
import com.jbproject.jutopia.constant.CommonConstatns;
import com.jbproject.jutopia.rest.dto.result.MenuResult;
import com.jbproject.jutopia.rest.dto.result.TopMenuResult;
import com.jbproject.jutopia.rest.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @ModelAttribute("userInfo")
    public AccessJwtToken.AccessJwtPrincipal userInfo(
            @AuthenticationPrincipal AccessJwtToken.AccessJwtPrincipal principal
    ) {
        return principal;
    }

    @ModelAttribute("topMenuInfo")
    public TopMenuResult getTopMenuInfo(HttpServletRequest request) {
        String requestUrl = request.getRequestURI();
        TopMenuResult result = new TopMenuResult();
        result.setMenuSize(curMenuList.size());
        result.setMenuList(curMenuList);

        if(defaultPermitAllPath.matches(request)){
            return result;
        }


        if(!requestUrl.contains("/admin")){
            curMenuList = menuService.getShowMenuList(CommonConstatns.MENU_ROLE_USER);
            result.setMenuSize(curMenuList.size());
            result.setMenuList(curMenuList);

            return result;
        }else{
            return result;
        }
    }
}
