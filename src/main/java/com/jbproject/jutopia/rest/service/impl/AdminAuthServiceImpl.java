package com.jbproject.jutopia.rest.service.impl;

import com.jbproject.jutopia.rest.entity.RoleEntity;
import com.jbproject.jutopia.rest.entity.RoleMenuRelation;
import com.jbproject.jutopia.rest.model.result.AuthResult;
import com.jbproject.jutopia.rest.repository.MenuRepository;
import com.jbproject.jutopia.rest.repository.RoleMenuRepository;
import com.jbproject.jutopia.rest.service.AdminAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminAuthServiceImpl implements AdminAuthService {

    private final MenuRepository menuRepository;
    private final RoleMenuRepository roleMenuRepository;

    public AuthResult getMenuRoleList(String roleType){
        return menuRepository.getMenuRoleList(roleType);
    }

    public void cudRoleMenu(String roleType, List<String> menuIds){
//        List<RoleMenuRelation> roleMenuRelations = roleMenuRepository.

        for(String menuId : menuIds ){
            RoleMenuRelation roleMenu = RoleMenuRelation.builder()
                    .roleId(roleType)
                    .menuId(Long.getLong(menuId))
                    .build();

            roleMenuRepository.save(roleMenu);
        }

    }
}
