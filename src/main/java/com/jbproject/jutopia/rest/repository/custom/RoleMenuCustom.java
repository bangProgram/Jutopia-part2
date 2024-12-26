package com.jbproject.jutopia.rest.repository.custom;

import com.jbproject.jutopia.rest.model.result.RoleMenuResult;

import java.util.List;

public interface RoleMenuCustom {

    List<RoleMenuResult> getRoleBasedWhiteList(String role);
    void deleteRoleMenuByRoleAndMenuIdList(String roleType, List<Long> menuIdList);

}
