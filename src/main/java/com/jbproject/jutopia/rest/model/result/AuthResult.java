package com.jbproject.jutopia.rest.model.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AuthResult {
    private String role;
    private List<MenuResult> userMenuRoleList;
    private List<MenuResult> adminMenuRoleList;
}
