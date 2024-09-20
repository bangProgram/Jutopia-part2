package com.jbproject.jutopia.auth.service;

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

    public UserEntity getUserInfo(LoginPayload payload){

        String email = payload.getEmail();
        String password = passwordEncoder.encode(payload.getPassword());

        Optional<UserEntity> user = userRepository.findByEmailAndPassword(email, password);
        if(!user.isPresent()) throw new ExceptionProvider(ErrorCodeConstants.AUTHENTICATION_400_01);

        return user.get();
    }
}
