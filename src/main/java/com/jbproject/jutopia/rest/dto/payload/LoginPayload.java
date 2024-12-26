package com.jbproject.jutopia.rest.dto.payload;

import lombok.Data;

@Data
public class LoginPayload {

    private String userId;
    private String password;
}
