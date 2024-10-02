package com.jbproject.jutopia.rest.service.impl;

import com.jbproject.jutopia.constant.ErrorCodeConstants;
import com.jbproject.jutopia.exception.ExceptionProvider;
import com.jbproject.jutopia.rest.entity.UserEntity;
import com.jbproject.jutopia.rest.model.payload.LoginPayload;
import com.jbproject.jutopia.rest.model.payload.SignupPayload;
import com.jbproject.jutopia.rest.repository.UserRepository;
import com.jbproject.jutopia.rest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
//    private final AccessJwtFacade accessJwtFacade;

    public void addUser(SignupPayload payload) {

        String password = passwordEncoder.encode(payload.getPassword());
        System.out.println("JB password 확인 : "+payload.getPassword()+ " / " + password);

        UserEntity newUser = UserEntity.builder()
                .userId(payload.getUserId())
                .email(payload.getEmail())
                .password(password)
                .name(payload.getName())
                .age(payload.getAge())
                .role("JUTOPIAN")
                .build();

        newUser.setCreateId(newUser.getEmail());

        userRepository.save(newUser);

        System.out.println("new User "+ newUser.getId());
    }

    public UserEntity findById(Long id){
        return userRepository.findById(id).orElseThrow(
                () -> new ExceptionProvider(ErrorCodeConstants.AUTHENTICATION_400_01)
        );
    }
}
