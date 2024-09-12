package com.jbproject.jutopia.auth.service.impl;

import com.jbproject.jutopia.auth.model.RoleBasedWhiteList;
import com.jbproject.jutopia.rest.entity.RoleMenuRelation;
import com.jbproject.jutopia.rest.entity.UserEntity;
import com.jbproject.jutopia.rest.model.payload.SignupPayload;
import com.jbproject.jutopia.rest.repository.RoleMenuRepository;
import com.jbproject.jutopia.rest.repository.UserRepository;
import com.jbproject.jutopia.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RoleMenuRepository roleMenuRepository;

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
}
