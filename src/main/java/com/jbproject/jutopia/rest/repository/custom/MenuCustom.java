package com.jbproject.jutopia.rest.repository.custom;

import com.jbproject.jutopia.rest.dto.result.AuthResult;
import com.jbproject.jutopia.rest.dto.result.MenuResult;

import java.util.List;

public interface MenuCustom {

    List<MenuResult> getMenuList(String menuType);
    List<MenuResult> getMenuList(String menuType, String showYn);

    AuthResult getMenuRoleList(String roleType);
}
