package com.jbproject.jutopia.rest.repository.custom;

import com.jbproject.jutopia.auth.model.RoleBasedWhiteList;
import com.jbproject.jutopia.rest.entity.RoleMenuRelation;

import java.util.List;

public interface RoleMenuCustom {

    List<RoleBasedWhiteList> getRoleBasedWhiteList();
    void deleteRoleMenuByRoleAndMenuIdList(String roleType, List<Long> menuIdList);

}
