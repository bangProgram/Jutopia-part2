package com.jbproject.jutopia.rest.service.impl;

import com.jbproject.jutopia.constant.CommonErrorCode;
import com.jbproject.jutopia.exception.ExceptionProvider;
import com.jbproject.jutopia.rest.entity.MenuEntity;
import com.jbproject.jutopia.rest.model.payload.MenuCudPayload;
import com.jbproject.jutopia.rest.model.result.MenuResult;
import com.jbproject.jutopia.rest.repository.MenuRepository;
import com.jbproject.jutopia.rest.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    public void addMenu(MenuCudPayload payload) {
        System.out.println("payload : "+payload.getParentId());
        MenuEntity newMenu = MenuEntity.builder()
                .menuName(payload.getMenuName())
                .menuUrl(payload.getMenuUrl())
                .useYn(payload.getUseYn())
                .seq(payload.getSeq())
                .menuType(payload.getMenuType())
                .menuDetail(payload.getMenuDetail())
                .build();

        if(payload.getParentId() != null){
            MenuEntity parentMenu = menuRepository.findById(payload.getParentId()).orElseThrow(
                    () -> new ExceptionProvider(CommonErrorCode.MENU_404_01)
            );
            newMenu.setParentMenu(parentMenu);
        }

        menuRepository.save(newMenu);
    }

    public void modMenu(MenuCudPayload payload) {
        MenuEntity curMenu = menuRepository.findById(payload.getMenuId()).orElseThrow(
                () -> new ExceptionProvider(CommonErrorCode.MENU_404_01)
        );

        curMenu.modMenu(payload);

        if(!payload.getParentId().equals(curMenu.getParentMenu().getId())){
            MenuEntity parentMenu = menuRepository.findById(payload.getParentId()).orElseThrow(
                    () -> new ExceptionProvider(CommonErrorCode.MENU_404_01)
            );
            curMenu.setParentMenu(parentMenu);
        }

        menuRepository.save(curMenu);
    }

    public List<MenuResult> getMenuList(String menuType){
        return menuRepository.getMenuList(menuType);
    }
}
