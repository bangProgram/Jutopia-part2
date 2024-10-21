package com.jbproject.jutopia.rest.service;

import com.jbproject.jutopia.rest.model.result.AuthResult;

import java.util.List;

public interface AdminAuthService {
    AuthResult getMenuRoleList(String roleType);
    void cudRoleMenu(String roleType, List<Long> menuIds, List<Long> cudMenuIds, List<Long> chkMenuIds);
}
