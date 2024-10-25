package com.jbproject.jutopia.rest.repository.custom.impl;

import com.jbproject.jutopia.rest.model.result.CommCodeResult;
import com.jbproject.jutopia.rest.repository.custom.CommCodeCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jbproject.jutopia.rest.entity.QCommCodeEntity.commCodeEntity;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CommCodeCustomImpl implements CommCodeCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public List<CommCodeResult> getCommCodeListByGroupCode(String groupCode) {

        return jpaQueryFactory.select(
                    Projections.fields(
                        CommCodeResult.class,
                        commCodeEntity.code.as("code"),
                        commCodeEntity.groupCode.as("groupCode"),
                        commCodeEntity.codeName.as("codeName"),
                        commCodeEntity.seq.as("seq"),
                        commCodeEntity.useYn.as("useYn")
                    )
            )
            .from(commCodeEntity)
            .where(commCodeEntity.groupCode.eq(groupCode))
            .orderBy(commCodeEntity.seq.asc())
            .fetch();
    }
}
