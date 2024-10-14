package com.jbproject.jutopia.rest.repository.custom.impl;

import com.jbproject.jutopia.constant.CommonConstatns;
import com.jbproject.jutopia.rest.entity.MenuEntity;
import com.jbproject.jutopia.rest.model.result.AuthResult;
import com.jbproject.jutopia.rest.model.result.MenuResult;
import com.jbproject.jutopia.rest.repository.custom.MenuCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.jbproject.jutopia.rest.entity.QMenuEntity.menuEntity;
import static com.jbproject.jutopia.rest.entity.QRoleMenuRelation.roleMenuRelation;
import static com.jbproject.jutopia.rest.entity.QRoleEntity.roleEntity;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MenuCustomImpl implements MenuCustom {

    private final JPAQueryFactory queryFactory;

    public List<MenuResult> getMenuList(String menuType){

        List<MenuEntity> menuEntities = queryFactory.selectFrom(menuEntity)
                .leftJoin(menuEntity.childMenu).fetchJoin() // 자식 메뉴를 LEFT JOIN FETCH
                .where(
                        menuEntity.menuType.eq(menuType)
                        .and(menuEntity.parentId.isNull())
                )
                .fetch();

        return menuEntities.stream().map(menu -> {
            MenuResult result = new MenuResult();
            result.setMenuId(menu.getId());
            result.setMenuName(menu.getMenuName());
            result.setMenuUrl(menu.getMenuUrl());
            result.setUseYn(menu.getUseYn());
            result.setSeq(menu.getSeq());
            result.setParentId(menu.getParentId());
            result.setChildMenu(menu.getChildMenu().stream().map(child -> {
                MenuResult childResult = new MenuResult();
                childResult.setMenuId(child.getId());
                childResult.setMenuName(child.getMenuName());
                childResult.setMenuUrl(child.getMenuUrl());
                childResult.setUseYn(child.getUseYn());
                childResult.setSeq(child.getSeq());
                childResult.setParentId(child.getParentId());
                return childResult;
            }).collect(Collectors.toList()));
            return result;
        }).collect(Collectors.toList());

//        return queryFactory.select(
//                Projections.fields(
//                        MenuResult.class
//                        ,menuEntity.id.as("menuId")
//                        ,menuEntity.menuName.as("menuName")
//                        ,menuEntity.menuUrl.as("menuUrl")
//                        ,menuEntity.useYn.as("useYn")
//                        ,menuEntity.seq.as("seq")
//                        ,menuEntity.parentId.as("parentId")
//                        ,menuEntity.childMenu.as("childMenu")
//                )
//        )
//        .from(menuEntity)
//        .where(whereCondition)
//        .fetch();
    }

    public AuthResult getMenuRoleList(String roleType){

        List<MenuResult> result = queryFactory.select(
                    Projections.fields(
                            MenuResult.class,
                            menuEntity.id.as("menuId"),
                            roleMenuRelation.roleEntity.role.as("roleId"),
                            menuEntity.menuName.as("menuName"),
                            menuEntity.menuDetail.as("menuDetail"),
                            menuEntity.menuUrl.as("menuUrl"),
                            menuEntity.useYn.as("useYn"),
                            menuEntity.seq.as("seq"),
                            menuEntity.parentId.as("parentId"),
                            menuEntity.menuType.as("menuType")
                    )
                )
                .from(menuEntity)
                .leftJoin(roleMenuRelation).on(menuEntity.id.eq(roleMenuRelation.menuEntity.id))
                .leftJoin(roleEntity).on(roleMenuRelation.roleEntity.role.eq(roleEntity.role).and(roleEntity.role.eq(roleType)))
                .fetch();

        List<MenuResult> userMenuRoleList;
        List<MenuResult> adminMenuRoleList;

        userMenuRoleList = result.stream()
                .filter(menu -> menu.getParentId() == null)
                .collect(Collectors.groupingBy(MenuResult::getMenuType)).get(CommonConstatns.MENU_ROLE_USER);

        for(MenuResult parent : userMenuRoleList){
            parent.setChildMenu(
                    result.stream()
                    .filter(menu -> menu.getParentId() != null)
                    .collect(Collectors.groupingBy(MenuResult::getParentId)).get(parent.getMenuId())
            );
        }

        adminMenuRoleList = result.stream()
                .filter(menu -> menu.getParentId() == null)
                .collect(Collectors.groupingBy(MenuResult::getMenuType)).get(CommonConstatns.MENU_ROLE_ADMIN);

        for(MenuResult parent : adminMenuRoleList){
            parent.setChildMenu(
                    result.stream()
                            .filter(menu -> menu.getParentId() != null)
                            .collect(Collectors.groupingBy(MenuResult::getParentId)).get(parent.getMenuId())
            );
        }

        AuthResult authResult = new AuthResult();
        authResult.setRole(result.getFirst().getRoleId());
        authResult.setUserMenuRoleList(userMenuRoleList);
        authResult.setAdminMenuRoleList(adminMenuRoleList);

        return authResult;
    }
}
