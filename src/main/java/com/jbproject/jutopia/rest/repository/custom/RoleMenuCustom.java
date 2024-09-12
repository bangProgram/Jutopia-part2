package com.jbproject.jutopia.rest.repository.custom;

import com.jbproject.jutopia.auth.model.RoleBasedWhiteList;

import java.util.List;

public interface RoleMenuCustom {

    List<RoleBasedWhiteList> getRoleBasedWhiteList();
}
