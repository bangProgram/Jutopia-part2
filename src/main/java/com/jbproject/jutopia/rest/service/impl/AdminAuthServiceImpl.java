package com.jbproject.jutopia.rest.service.impl;

import com.jbproject.jutopia.rest.entity.relation.RoleMenuRelation;
import com.jbproject.jutopia.rest.dto.result.AuthResult;
import com.jbproject.jutopia.rest.repository.MenuRepository;
import com.jbproject.jutopia.rest.repository.RoleMenuRepository;
import com.jbproject.jutopia.rest.service.AdminAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AdminAuthServiceImpl implements AdminAuthService {

    private final MenuRepository menuRepository;
    private final RoleMenuRepository roleMenuRepository;

    public AuthResult getMenuRoleList(String roleType){
        return menuRepository.getMenuRoleList(roleType);
    }

    public void cudRoleMenu(String roleType, List<Long> menuIds, List<Long> cudMenuIds, List<Long> chkMenuIds){
//        List<RoleMenuRelation> roleMenuRelations = roleMenuRepository.

        roleMenuRepository.deleteRoleMenuByRoleAndMenuIdList(roleType,chkMenuIds);
        roleMenuRepository.flush();

        if(menuIds != null){
            for(Long menuId : menuIds ){
                RoleMenuRelation roleMenu = RoleMenuRelation.builder()
                        .roleId(roleType)
                        .menuId(menuId)
                        .isCud(cudMenuIds != null && cudMenuIds.contains(menuId) ? "Y" : "N")
                        .build();

                roleMenuRepository.save(roleMenu);
            }
        }

    }
}
