package com.jbproject.jutopia.config.security;

import com.jbproject.jutopia.auth.service.AuthService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@Getter
public class RolebasedAuthConfig {

    @Autowired
    private AuthService authService;

    @Bean("roleBasedAuthList")
    public Map<String, List<String>> roleBasedAuthList(){
        return authService.getAllRoleBasedUrls();
    }
}
