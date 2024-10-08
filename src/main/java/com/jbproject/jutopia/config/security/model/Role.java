package com.jbproject.jutopia.config.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    VISITOR ("VISITOR,JUTOPIAN,TRADER,FUNDMANAGER,SYSTEM,ADMIN"),
    JUTOPIAN ("JUTOPIAN,TRADER,FUNDMANAGER,SYSTEM,ADMIN"),
    TRADER ("TRADER,FUNDMANAGER,SYSTEM,ADMIN"),
    FUNDMANAGER ("FUNDMANAGER,SYSTEM,ADMIN"),
    ADMIN ("SYSTEM,ADMIN"),
    SYSTEM ("SYSTEM");

    private final String accessRole;

    public static String getAccessRole(String role){
        return Role.valueOf(role).accessRole;
    }

}
