package com.jbproject.jutopia.rest.service;

import com.jbproject.jutopia.rest.model.payload.MenuCudPayload;

public interface MenuService {

    void addMenu(MenuCudPayload payload);
    void modMenu(MenuCudPayload payload);
}
