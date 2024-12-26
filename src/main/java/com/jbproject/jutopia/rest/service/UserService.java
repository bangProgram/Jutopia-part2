package com.jbproject.jutopia.rest.service;

import com.jbproject.jutopia.rest.entity.UserEntity;
import com.jbproject.jutopia.rest.dto.payload.SignupPayload;

public interface UserService {

    void addUser(SignupPayload payload);
    UserEntity findById(Long id);
}
