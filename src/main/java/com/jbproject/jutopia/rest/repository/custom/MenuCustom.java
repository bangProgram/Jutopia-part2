package com.jbproject.jutopia.rest.repository.custom;

import com.jbproject.jutopia.rest.model.result.AuthResult;
import com.jbproject.jutopia.rest.model.result.MenuResult;

import java.util.List;

public interface MenuCustom {

    List<MenuResult> getMenuList(String menuType);
    List<MenuResult> getMenuList(String menuType, String showYn);

    AuthResult getMenuRoleList(String roleType);
}
