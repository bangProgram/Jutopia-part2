package com.jbproject.jutopia.auth.service;

import com.jbproject.jutopia.config.security.model.UserDetail;
import com.jbproject.jutopia.constant.ErrorCodeConstants;
import com.jbproject.jutopia.exception.ExceptionProvider;
import com.jbproject.jutopia.rest.entity.RoleMenuRelation;
import com.jbproject.jutopia.rest.entity.UserEntity;
import com.jbproject.jutopia.rest.model.payload.LoginPayload;
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
public class AuthService implements UserDetailsService {

    private final RoleMenuRepository roleMenuRepository;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Map<String, List<String>> getAllRoleBasedUrls() {

        List<RoleMenuRelation> roleMenuRelations = roleMenuRepository.findAll();  // 모든 역할 목록 가져오기

        Map<String, List<String>> roleBasedWhiteList = roleMenuRelations.stream().collect(
                Collectors.groupingBy(
                        RoleMenuRelation::getRoleId,
                        Collectors.mapping(RoleMenuRelation::getUrl, Collectors.toList())
                )
        );

        return roleBasedWhiteList;
    }

    public boolean passwordMatcher(String password, String encodePassowrd) {
        return passwordEncoder.matches(password, encodePassowrd);
    }

    @Override
    public UserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserId(username);

        if (null == user) {
            throw new UsernameNotFoundException("Not found : " + username);
        }

        return new UserDetail(user);
    }
}
