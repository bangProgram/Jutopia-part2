package com.jbproject.jutopia.auth.service;

import com.jbproject.jutopia.rest.entity.UserEntity;
import com.jbproject.jutopia.rest.dto.result.RoleMenuResult;
import com.jbproject.jutopia.rest.repository.RoleMenuRepository;
import com.jbproject.jutopia.rest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
