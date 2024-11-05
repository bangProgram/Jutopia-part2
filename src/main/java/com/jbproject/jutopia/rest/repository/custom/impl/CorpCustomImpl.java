package com.jbproject.jutopia.rest.repository.custom.impl;

import com.jbproject.jutopia.constant.ServerUtilConstant;
import com.jbproject.jutopia.rest.entity.CorpEntity;
import com.jbproject.jutopia.rest.model.payload.MergeCorpDetailPayload;
import com.jbproject.jutopia.rest.model.result.CorpResult;
import com.jbproject.jutopia.rest.repository.custom.CorpCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static com.jbproject.jutopia.rest.entity.QCorpEntity.corpEntity;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CorpCustomImpl implements CorpCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public List<CorpResult> getCorpListByMergeCorpDetailPayload(MergeCorpDetailPayload payload) {
        System.out.println("test4" + (payload.getGubn() != null));
        BooleanBuilder whereCondition = new BooleanBuilder();
        System.out.println("test5");
        if(payload.getGubn() != null && !payload.getGubn().isEmpty()){
            whereCondition.and(corpEntity.stockCode.ne(""));
        }
        System.out.println("test6");
        return jpaQueryFactory.select(
                Projections.fields(
                        CorpResult.class,
                        corpEntity.corpCode
                        ,corpEntity.corpName
                        ,corpEntity.stockCode
                        ,corpEntity.modifyDate
                )
        ).from(corpEntity)
        .where(whereCondition)
        .orderBy(corpEntity.corpCode.asc())
        //.offset((long) Integer.parseInt(payload.getPage()) * Integer.parseInt(ServerUtilConstant.CORP_MERGE_LIMIT.getValue()))
        //.limit(Integer.parseInt(ServerUtilConstant.CORP_MERGE_LIMIT.getValue()))
        .fetch();

    }
}
