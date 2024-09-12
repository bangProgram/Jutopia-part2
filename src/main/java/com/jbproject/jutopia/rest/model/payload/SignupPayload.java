package com.jbproject.jutopia.rest.model.payload;

import lombok.Data;

@Data
public class SignupPayload {

    private String email;
    private String password;
    private String name;
    private int age;
}
