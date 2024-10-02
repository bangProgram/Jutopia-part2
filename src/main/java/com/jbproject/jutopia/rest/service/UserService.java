package com.jbproject.jutopia.rest.service;

import com.jbproject.jutopia.rest.entity.UserEntity;
import com.jbproject.jutopia.rest.model.payload.LoginPayload;
import com.jbproject.jutopia.rest.model.payload.SignupPayload;

public interface UserService {

    void addUser(SignupPayload payload);
    UserEntity findById(Long id);
}
