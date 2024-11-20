package com.jbproject.jutopia.rest.repository.custom.impl;

import com.jbproject.jutopia.rest.entity.CorpCisEntity;
import com.jbproject.jutopia.rest.model.result.CorpCisResult;
import com.jbproject.jutopia.rest.repository.custom.CorpCisCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jbproject.jutopia.rest.entity.QCorpCisEntity.corpCisEntity;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CorpCisCustomImpl implements CorpCisCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public List<CorpCisEntity> getAll1(){
        return jpaQueryFactory.selectFrom(corpCisEntity).orderBy(
                corpCisEntity.id.corpCode.asc(),
                corpCisEntity.id.accountId.asc(),
                corpCisEntity.id.bsnsYear.asc(),
                corpCisEntity.id.quarterlyReportCode.asc()
        ).fetch();
    }
    public List<CorpCisResult> getAll(){
        return jpaQueryFactory.select(
                        Projections.fields(
                                CorpCisResult.class,
                                corpCisEntity.id.corpCode
                                ,corpCisEntity.id.accountId
                                ,corpCisEntity.id.bsnsYear
                                ,corpCisEntity.id.quarterlyReportCode
                                ,corpCisEntity.quarterlyReportName
                                ,corpCisEntity.closingDate
                                ,corpCisEntity.accountName
                                ,corpCisEntity.netAmount
                                ,corpCisEntity.accumulatedNetAmount
                                ,corpCisEntity.befNetAmount
                                ,corpCisEntity.befAccumulatedNetAmount
                                ,corpCisEntity.currency
                        )
                )
                .from(corpCisEntity).fetch();
    }

    public List<CorpCisEntity> getByAccountIds1(List<String> accountIds){
        return jpaQueryFactory.selectFrom(corpCisEntity).where(
                corpCisEntity.id.accountId.in(accountIds)
        ).orderBy(
                corpCisEntity.id.corpCode.asc(),
                corpCisEntity.id.accountId.asc(),
                corpCisEntity.id.bsnsYear.asc(),
                corpCisEntity.id.quarterlyReportCode.asc()
        ).fetch();
    }

    public List<CorpCisResult> getByAccountIds2(List<String> accountIds){
        return jpaQueryFactory.select(
            Projections.fields(
                    CorpCisResult.class,
                    corpCisEntity
            )
        )
        .from(corpCisEntity)
        .where(
        corpCisEntity.id.accountId.in(accountIds)
        ).orderBy(
                corpCisEntity.id.corpCode.asc(),
                corpCisEntity.id.accountId.asc(),
                corpCisEntity.id.bsnsYear.asc(),
                corpCisEntity.id.quarterlyReportCode.asc()
        ).fetch();
    }
}
