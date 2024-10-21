package com.jbproject.jutopia.auth.service;

import com.jbproject.jutopia.auth.model.RoleBasedWhiteList;
import com.jbproject.jutopia.constant.ErrorCodeConstants;
import com.jbproject.jutopia.exception.ExceptionProvider;
import com.jbproject.jutopia.rest.entity.RoleMenuRelation;
import com.jbproject.jutopia.rest.entity.UserEntity;
import com.jbproject.jutopia.rest.model.payload.LoginPayload;
import com.jbproject.jutopia.rest.model.result.RoleMenuResult;
import com.jbproject.jutopia.rest.repository.RoleMenuRepository;
import com.jbproject.jutopia.rest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final RoleMenuRepository roleMenuRepository;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<RoleMenuResult> getRoleBasedWhiteList(String role) {

        return roleMenuRepository.getRoleBasedWhiteList(role);
    }

    public boolean passwordMatcher(String password, String encodePassowrd) {
        return passwordEncoder.matches(password, encodePassowrd);
    }

    public UserEntity getUserInfoByUserid (String userId) {
        return userRepository.findByUserId(userId);
    }
}
