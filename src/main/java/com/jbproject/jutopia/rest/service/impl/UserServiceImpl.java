package com.jbproject.jutopia.rest.service.impl;

import com.jbproject.jutopia.config.security.jwt.AccessJwtFacade;
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
    private final AccessJwtFacade accessJwtFacade;

    public void addUser(SignupPayload payload) {

        String password = passwordEncoder.encode(payload.getPassword());
        System.out.println("JB password 확인 : "+payload.getPassword()+ " / " + password);

        UserEntity newUser = UserEntity.builder()
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

    public void login(LoginPayload payload) {
        String email = payload.getEmail();
        String password = passwordEncoder.encode(payload.getPassword());

        Optional<UserEntity> user = userRepository.findByEmailAndPassword(email, password);

        // 해당 사용자 없을 경우 반환처리
        if(!user.isPresent()){
            throw new ExceptionProvider(ErrorCodeConstants.AUTHENTICATION_400_01);
        }

        UserEntity userInfo = user.get();

        String token = accessJwtFacade.createToken(
                AccessJwtFacade.CustomClaims.builder()
                        .email(userInfo.getEmail())
                        .role(userInfo.getRole())
                        .socialStatus(
                                AccessJwtFacade.CustomClaims.SocialStatus.builder()
                                        .socialType(userInfo.getSocialType())
                                        .socialId(userInfo.getSocialId())
                                        .build()
                        )
                        .build()
        );

    }
}
