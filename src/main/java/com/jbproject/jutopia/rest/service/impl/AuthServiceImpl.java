package com.jbproject.jutopia.rest.service.impl;

import com.jbproject.jutopia.rest.entity.UserEntity;
import com.jbproject.jutopia.rest.model.payload.SignupPayload;
import com.jbproject.jutopia.rest.repository.UserRepository;
import com.jbproject.jutopia.rest.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public void addUser(SignupPayload payload) {
        UserEntity newUser = userRepository.save(
                UserEntity.builder()
                        .email(payload.getEmail())
                        .name(payload.getName())
                        .age(payload.getAge())
                        .role("JUTOPIAN")
                        .build()
        );

        System.out.println("new User "+ newUser.getId());
    }

    public void loginProc(String email) {

    }
}
