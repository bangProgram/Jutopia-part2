package com.jbproject.jutopia.rest.repository.custom.impl;

import com.jbproject.jutopia.constant.CommonConstatns;
import com.jbproject.jutopia.rest.entity.MenuEntity;
import com.jbproject.jutopia.rest.entity.RoleMenuRelation;
import com.jbproject.jutopia.rest.model.result.AuthResult;
import com.jbproject.jutopia.rest.model.result.MenuResult;
import com.jbproject.jutopia.rest.model.result.RoleMenuResult;
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

    public List<MenuResult> getMenuListByParentId (String parentId) {
        BooleanBuilder whereCondition = new BooleanBuilder();
        if(parentId != null){
            whereCondition.and(menuEntity.parentId.eq(Long.parseLong(parentId)));
        }else{
            whereCondition.and(menuEntity.parentId.isNull());
        }

        return null;
    }

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
            result.setMenuType(menu.getMenuType());
            result.setShowYn(menu.getShowYn());
            result.setChildMenu(menu.getChildMenu().stream().map(child -> {
                MenuResult childResult = new MenuResult();
                childResult.setMenuId(child.getId());
                childResult.setMenuName(child.getMenuName());
                childResult.setMenuUrl(child.getMenuUrl());
                childResult.setUseYn(child.getUseYn());
                childResult.setSeq(child.getSeq());
                childResult.setParentId(child.getParentId());
                childResult.setMenuType(child.getMenuType());
                childResult.setShowYn(child.getShowYn());
                return childResult;
            }).collect(Collectors.toList()));
            return result;
        }).collect(Collectors.toList());

    }

    public List<MenuResult> getMenuList(String menuType, String showYn){

        List<MenuEntity> menuEntities = queryFactory.selectFrom(menuEntity)
                .leftJoin(menuEntity.childMenu).fetchJoin() // 자식 메뉴를 LEFT JOIN FETCH
                .where(
                        menuEntity.menuType.eq(menuType)
                                .and(menuEntity.parentId.isNull())
                                .and(menuEntity.showYn.eq(showYn))
                )
                .orderBy(menuEntity.seq.asc())
                .fetch();

        return menuEntities.stream().map(menu -> {
            MenuResult result = new MenuResult();
            result.setMenuId(menu.getId());
            result.setMenuName(menu.getMenuName());
            result.setMenuUrl(menu.getMenuUrl());
            result.setUseYn(menu.getUseYn());
            result.setSeq(menu.getSeq());
            result.setParentId(menu.getParentId());
            result.setMenuType(menu.getMenuType());
            result.setShowYn(menu.getShowYn());
            result.setChildMenu(menu.getChildMenu().stream().map(child -> {
                MenuResult childResult = new MenuResult();
                childResult.setMenuId(child.getId());
                childResult.setMenuName(child.getMenuName());
                childResult.setMenuUrl(child.getMenuUrl());
                childResult.setUseYn(child.getUseYn());
                childResult.setSeq(child.getSeq());
                childResult.setParentId(child.getParentId());
                child.setMenuType(child.getMenuType());
                childResult.setShowYn(child.getShowYn());
                return childResult;
            }).collect(Collectors.toList()));
            return result;
        }).collect(Collectors.toList());

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
                            menuEntity.menuType,
                            menuEntity.showYn,
                            Projections.fields(
                                    RoleMenuResult.class,
                                    roleMenuRelation.roleId,
                                    roleMenuRelation.menuId,
                                    menuEntity.menuUrl,
                                    roleMenuRelation.isCud
                            ).as("roleMenuResult")
                    )
                )
                .from(menuEntity)
                .leftJoin(roleMenuRelation).on(menuEntity.id.eq(roleMenuRelation.menuEntity.id).and(roleEntity.role.eq(roleType)))
                .leftJoin(roleEntity).on(roleMenuRelation.roleEntity.role.eq(roleEntity.role))
                .fetch();

        List<MenuResult> userMenuRoleList = result.stream()
                .filter(menu -> menu.getParentId() == null)
                .collect(Collectors.groupingBy(MenuResult::getMenuType))
                .get(CommonConstatns.MENU_ROLE_USER);

        if(userMenuRoleList != null){
            for(MenuResult parent : userMenuRoleList){
                parent.setChildMenu(
                        result.stream()
                                .filter(menu -> menu.getParentId() != null)
                                .collect(Collectors.groupingBy(MenuResult::getParentId)).get(parent.getMenuId())
                );
            }
        }else{
            userMenuRoleList = new ArrayList<>();
        }

        List<MenuResult> adminMenuRoleList = result.stream()
                .filter(menu -> menu.getParentId() == null)
                .collect(Collectors.groupingBy(MenuResult::getMenuType))
                .get(CommonConstatns.MENU_ROLE_ADMIN);

        if(adminMenuRoleList != null){
            for(MenuResult parent : adminMenuRoleList){
                parent.setChildMenu(
                        result.stream()
                                .filter(menu -> menu.getParentId() != null)
                                .collect(Collectors.groupingBy(MenuResult::getParentId)).get(parent.getMenuId())
                );
            }
        }else{
            adminMenuRoleList = new ArrayList<>();
        }

        AuthResult authResult = new AuthResult();
        authResult.setRole(roleType);
        authResult.setUserMenuRoleList(userMenuRoleList);
        authResult.setAdminMenuRoleList(adminMenuRoleList);

        return authResult;
    }
}
