package com.jbproject.jutopia.rest.dto.payload;

import lombok.Data;

@Data
public class SignupPayload {

    private String userId;
    private String email;
    private String password;
    private String name;
    private int age;
}
