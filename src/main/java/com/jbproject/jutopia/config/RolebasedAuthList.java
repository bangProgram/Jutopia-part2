package com.jbproject.jutopia.config;

import com.jbproject.jutopia.auth.service.AuthService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@Getter
public class RolebasedAuthList {

    @Autowired
    private AuthService authService;

    @Bean
    public Map<String, List<String>> roleBasedAuthList1(){
        return authService.getAllRoleBasedUrls();
    }
}
