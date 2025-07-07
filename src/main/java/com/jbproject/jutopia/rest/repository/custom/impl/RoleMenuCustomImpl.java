package com.jbproject.jutopia.rest.repository.custom.impl;

import com.jbproject.jutopia.rest.dto.result.RoleMenuResult;
import com.jbproject.jutopia.rest.repository.custom.RoleMenuCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jbproject.jutopia.rest.entity.QMenuEntity.menuEntity;
import static com.jbproject.jutopia.rest.entity.relation.QRoleMenuRelation.roleMenuRelation;



@Repository
@RequiredArgsConstructor
public class RoleMenuCustomImpl implements RoleMenuCustom {

    private final JPAQueryFactory queryFactory;

    public List<RoleMenuResult> getRoleBasedWhiteList(String role){

        BooleanBuilder whereCondition = new BooleanBuilder();

        if(!role.equals("ALL")){
            whereCondition.and(roleMenuRelation.roleId.eq(role));
        }

        return queryFactory.select(
                Projections.fields(
                        RoleMenuResult.class,
                        roleMenuRelation.roleId.as("roleId"),
                        roleMenuRelation.menuId.as("menuId"),
                        menuEntity.menuUrl.as("menuUrl"),
                        roleMenuRelation.isCud.as("isCud")
                    )
                )
                .from(menuEntity)
                .innerJoin(roleMenuRelation).on(menuEntity.id.eq(roleMenuRelation.menuEntity.id))
                .where(whereCondition)
                .fetch();
    }

    public void deleteRoleMenuByRoleAndMenuIdList(String roleType, List<Long> menuIdList) {
        queryFactory.delete(roleMenuRelation)
                .where(
                        roleMenuRelation.roleId.eq(roleType)
                        .and(roleMenuRelation.menuId.in(menuIdList))
                )
                .execute()
        ;
    }
}
