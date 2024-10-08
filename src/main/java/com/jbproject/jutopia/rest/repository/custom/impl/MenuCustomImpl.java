package com.jbproject.jutopia.rest.repository.custom.impl;

import com.jbproject.jutopia.rest.model.result.MenuResult;
import com.jbproject.jutopia.rest.repository.custom.MenuCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.jbproject.jutopia.rest.entity.QMenuEntity.menuEntity;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MenuCustomImpl implements MenuCustom {

    private final JPAQueryFactory queryFactory;

    public List<MenuResult> getMenuList(String menuId){

        System.out.println("어디서 에러?3");
        BooleanBuilder whereCondition = new BooleanBuilder();
        if(menuId != null){
            whereCondition.and(menuEntity.parentId.eq(Long.parseLong(menuId)));
        }else{
            whereCondition.and(menuEntity.parentId.isNull());
        }

        return queryFactory.select(
                Projections.fields(
                        MenuResult.class
                        ,menuEntity.id.as("id")
                        ,menuEntity.menuName.as("menuName")
                        ,menuEntity.menuUrl.as("menuUrl")
                        ,menuEntity.useYn.as("useYn")
                        ,menuEntity.seq.as("seq")
                        ,menuEntity.parentId.as("parentId")
                )
        )
        .from(menuEntity)
        .where(whereCondition)
        .fetch();
    }

}
