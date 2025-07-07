package com.jbproject.jutopia.config.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    ALL ("ALL"),
    VISITOR ("VISITOR"),
    JUTOPIAN ("VISITOR,JUTOPIAN"),
    TRADER ("VISITOR,JUTOPIAN,TRADER"),
    FUNDMANAGER ("VISITOR,JUTOPIAN,TRADER,FUNDMANAGER"),
    ADMIN ("VISITOR,JUTOPIAN,TRADER,FUNDMANAGER,ADMIN"),
    SYSTEM ("VISITOR,JUTOPIAN,TRADER,FUNDMANAGER,ADMIN,SYSTEM");

    private final String accessRole;

    public static String getAccessRole(String role){
        return Role.valueOf(role).accessRole;
    }

}
