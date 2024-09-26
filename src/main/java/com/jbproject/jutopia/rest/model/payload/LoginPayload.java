package com.jbproject.jutopia.rest.model.payload;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
public class LoginPayload {

    private String userId;
    private String password;
}
