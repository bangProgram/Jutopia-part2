package com.jbproject.jutopia.rest.model.payload;

import lombok.Data;

@Data
public class LoginPayload {

    private String email;
    private String password;
}
