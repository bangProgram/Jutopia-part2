package com.jbproject.jutopia.rest.repository.custom.impl;

import com.jbproject.jutopia.auth.model.RoleBasedWhiteList;
import com.jbproject.jutopia.rest.repository.custom.RoleMenuCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jbproject.jutopia.rest.entity.QMenuEntity.menuEntity;
import static com.jbproject.jutopia.rest.entity.QRoleMenuRelation.roleMenuRelation;



@Repository
@RequiredArgsConstructor
public class RoleMenuCustomImpl implements RoleMenuCustom {

    private final JPAQueryFactory queryFactory;

    public List<RoleBasedWhiteList> getRoleBasedWhiteList(){
        return queryFactory.select(
                Projections.fields(
                    RoleBasedWhiteList.class,
                    roleMenuRelation.roleId.as("role"),
                    menuEntity.menuUrl.as("url")
                    )
                )
                .from(menuEntity)
                .innerJoin(roleMenuRelation).on(menuEntity.id.eq(roleMenuRelation.menuEntity.id))
                .fetch();
    }
}
