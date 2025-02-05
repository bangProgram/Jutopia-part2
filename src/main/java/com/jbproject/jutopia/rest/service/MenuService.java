package com.jbproject.jutopia.rest.service;

import com.jbproject.jutopia.rest.dto.payload.MenuCudPayload;
import com.jbproject.jutopia.rest.dto.result.MenuResult;

import java.util.List;

public interface MenuService {

    void addMenu(MenuCudPayload payload);
    void modMenu(MenuCudPayload payload);
    void delMenu(MenuCudPayload payload);
    List<MenuResult> getMenuList(String menuType);
    List<MenuResult> getShowMenuList(String menuType);
}
