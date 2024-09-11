package com.jbproject.jutopia.config.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    VISITOR ("ROLE_VISITOR"),
    JUTOPIAN ("ROLE_JUTOPIAN,ROLE_VISITOR"),
    TRADER ("ROLE_TRADER,ROLE_JUTOPIAN,ROLE_VISITOR"),
    FUNDMANAGER ("ROLE_FUNDMANAGER,ROLE_TRADER,ROLE_JUTOPIAN,ROLE_VISITOR"),
    ADMIN ("ROLE_ADMIN,ROLE_FUNDMANAGER,ROLE_TRADER,ROLE_JUTOPIAN,ROLE_VISITOR"),
    SYSTEM ("ROLE_SYSTEM,ROLE_ADMIN,ROLE_FUNDMANAGER,ROLE_TRADER,ROLE_JUTOPIAN,ROLE_VISITOR");

    private final String roles;

    public static String getIncludingRoles(String role){
        return Role.valueOf(role).getRoles();
    }

}
