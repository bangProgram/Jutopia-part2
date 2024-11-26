package com.jbproject.jutopia.rest.repository.custom.impl;

import com.jbproject.jutopia.rest.entity.CorpCisEntity;
import com.jbproject.jutopia.rest.model.CorpCisModel;
import com.jbproject.jutopia.rest.model.CorpCisStatModel;
import com.jbproject.jutopia.rest.model.payload.MergeCorpCisStatPayload;
import com.jbproject.jutopia.rest.model.payload.SearchCorpPayload;
import com.jbproject.jutopia.rest.model.result.CorpCisResult;
import com.jbproject.jutopia.rest.repository.custom.CorpCisCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.jbproject.jutopia.rest.entity.QCorpCisEntity.corpCisEntity;
import static com.jbproject.jutopia.rest.entity.QCorpDetailEntity.corpDetailEntity;

import static org.springframework.util.StringUtils.hasText;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CorpCisCustomImpl implements CorpCisCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public List<CorpCisModel> getCorpCisList(MergeCorpCisStatPayload payload){

        BooleanBuilder whereCondition = whereCorpCisList(payload);

        return jpaQueryFactory.select(
                        Projections.fields(
                                CorpCisModel.class,
                                corpCisEntity.id.stockCode
                                ,corpDetailEntity.stockName
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
                .from(corpCisEntity)
                .innerJoin(corpDetailEntity)
                .on(corpCisEntity.id.stockCode.eq(corpDetailEntity.stockCode))
                .where(whereCondition)
                .fetch();
    }

    public List<CorpCisModel> searchCorpCisList(SearchCorpPayload payload) {
        BooleanBuilder whereCondition = whereSearchCorpCisList(payload);

        return jpaQueryFactory.select(
                Projections.fields(
                        CorpCisModel.class
                        ,corpCisEntity.id.stockCode
                        ,corpCisEntity.id.bsnsYear
                        ,corpCisEntity.id.quarterlyReportCode
                        ,corpCisEntity.id.accountId
                        ,corpCisEntity.quarterlyReportName
                        ,corpCisEntity.closingDate
                        ,corpCisEntity.accountName
                        ,corpCisEntity.netAmount
                        ,corpCisEntity.accumulatedNetAmount
                        ,corpCisEntity.befNetAmount
                        ,corpCisEntity.befAccumulatedNetAmount
                        ,corpCisEntity.currency
                )
        ).from(corpCisEntity)
        .where(whereCondition)
        .fetch();
    }

    public List<CorpCisStatModel> getCorpCisStatList(MergeCorpCisStatPayload payload){

        BooleanBuilder whereCondition = whereCorpCisList(payload);

        return jpaQueryFactory.select(
                        Projections.fields(
                                CorpCisStatModel.class,
                                corpCisEntity.id.stockCode
                                ,corpCisEntity.id.accountId
                                ,corpCisEntity.id.bsnsYear
                                ,corpCisEntity.id.quarterlyReportCode
                                ,corpDetailEntity.stockName
                                ,corpCisEntity.quarterlyReportName
                                ,corpCisEntity.closingDate
                                ,corpCisEntity.accountName
                                ,corpCisEntity.netAmount
                                ,corpCisEntity.accumulatedNetAmount
                                ,corpCisEntity.befNetAmount
                                ,corpCisEntity.befAccumulatedNetAmount.as("getBefAccumulatedNetAmount")
                                ,corpCisEntity.currency
                        )
                )
                .from(corpCisEntity)
                .innerJoin(corpDetailEntity)
                .on(corpCisEntity.id.stockCode.eq(corpDetailEntity.stockCode))
                .where(whereCondition)
                .fetch();
    }

    public BooleanBuilder whereCorpCisList(MergeCorpCisStatPayload payload){
        BooleanBuilder result = new BooleanBuilder();

        if(hasText(payload.getStockCode())){
            result.and(corpCisEntity.id.stockCode.eq(payload.getStockCode()));
        }
        if(hasText(payload.getAccountId())){
            result.and(corpCisEntity.id.accountId.eq(payload.getAccountId()));
        }
        if(hasText(payload.getBsnsYear())){
            result.and(corpCisEntity.id.bsnsYear.eq(payload.getBsnsYear()));
        }
        if(hasText(payload.getQuarterlyReportCode())){
            result.and(corpCisEntity.id.quarterlyReportCode.eq(payload.getQuarterlyReportCode()));
        }
        if(payload.getCorpCls() != null){
            result.and(corpDetailEntity.corpCls.in(payload.getCorpCls()));
        }
        return result;
    }

    public BooleanBuilder whereSearchCorpCisList(SearchCorpPayload payload){
        BooleanBuilder result = new BooleanBuilder();

        if(hasText(payload.getStockCode())){
            result.and(corpCisEntity.id.stockCode.eq(payload.getStockCode()));
        }
        if(hasText(payload.getStockName())){
            result.and(corpDetailEntity.stockName.like("%"+payload.getStockName()+"%"));
        }
        if(payload.getAccountIds() != null){
            result.and(corpCisEntity.id.accountId.in(payload.getAccountIds()));
        }
        if(hasText(payload.getStartBsnsYear()) && hasText(payload.getEndBsnsYear())){
            result.and(corpCisEntity.id.bsnsYear.between(payload.getStartBsnsYear(), payload.getEndBsnsYear()));
        }
        return result;
    }
}
