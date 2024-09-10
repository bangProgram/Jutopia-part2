package com.jbproject.jutopia.rest.service;

import com.jbproject.jutopia.rest.model.payload.SignupPayload;

public interface AuthService {

    void addUser(SignupPayload payload);
}
