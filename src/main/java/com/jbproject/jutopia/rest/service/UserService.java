package com.jbproject.jutopia.rest.service;

import com.jbproject.jutopia.rest.model.payload.LoginPayload;
import com.jbproject.jutopia.rest.model.payload.SignupPayload;

public interface UserService {

    void addUser(SignupPayload payload);
}
