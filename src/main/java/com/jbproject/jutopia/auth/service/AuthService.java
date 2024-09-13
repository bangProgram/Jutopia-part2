package com.jbproject.jutopia.auth.service;

import com.jbproject.jutopia.rest.model.payload.SignupPayload;

import java.util.List;
import java.util.Map;

public interface AuthService {

    Map<String, List<String>> getAllRoleBasedUrls();

}
