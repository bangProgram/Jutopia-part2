package com.jbproject.jutopia.rest.dto.result;

import lombok.Data;

import java.util.List;

@Data
public class AuthResult {
    private String role;
    private List<MenuResult> userMenuRoleList;
    private List<MenuResult> adminMenuRoleList;
}
