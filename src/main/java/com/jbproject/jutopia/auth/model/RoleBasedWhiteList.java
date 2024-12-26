package com.jbproject.jutopia.auth.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleBasedWhiteList {

    private String role;
    private String isCud;
    private String url;
}
