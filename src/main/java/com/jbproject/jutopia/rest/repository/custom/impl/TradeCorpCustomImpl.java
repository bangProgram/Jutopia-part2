package com.jbproject.jutopia.rest.repository.custom.impl;

import com.jbproject.jutopia.rest.dto.payload.SearchTradeCorpPayload;
import com.jbproject.jutopia.rest.dto.result.TradeCorpResult;
import com.jbproject.jutopia.rest.repository.custom.TradeCorpCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static com.jbproject.jutopia.rest.entity.QNyCorpDetailEntity.nyCorpDetailEntity;
import static com.jbproject.jutopia.rest.entity.QTradeCorpEntity.tradeCorpEntity;
import static com.jbproject.jutopia.rest.entity.QTradeCorpDetailEntity.tradeCorpDetailEntity;

@Repository
@Slf4j
@RequiredArgsConstructor
public class TradeCorpCustomImpl implements TradeCorpCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public List<TradeCorpResult> searchTradeCorpList(SearchTradeCorpPayload payload) {

        BooleanBuilder whereCondition = new BooleanBuilder();
        if(hasText(payload.getSearchWord())){
            whereCondition.and(
                    tradeCorpEntity.stockCode.like("%"+payload.getSearchWord()+"%").or(
                            tradeCorpEntity.stockName.like("%"+payload.getSearchWord()+"%")
                    )
            );
        }

        return jpaQueryFactory.select(
                Projections.fields(
                        TradeCorpResult.class,
                        tradeCorpEntity.stockCode,
                        tradeCorpEntity.stockName,
                        nyCorpDetailEntity.openPrice,
                        nyCorpDetailEntity.closePrice,
                        (tradeCorpDetailEntity.buyQuantity.sum().subtract(tradeCorpDetailEntity.sellQuantity.sum())).as("quantity"),
                        (tradeCorpDetailEntity.buyAmount.sum().divide(tradeCorpDetailEntity.buyQuantity.sum().subtract(tradeCorpDetailEntity.sellQuantity.sum()))).as("avgAmount"),
                        (tradeCorpDetailEntity.profitLossAmount.sum()).as("totalProfitLossAmount")
                )
        ).from(tradeCorpEntity)
        .innerJoin(nyCorpDetailEntity)
        .on(tradeCorpEntity.stockCode.eq(nyCorpDetailEntity.cikCode))
        .leftJoin(tradeCorpDetailEntity)
        .on(tradeCorpEntity.id.eq(tradeCorpDetailEntity.tradeCorpEntity.id))
        .where(whereCondition)
        .groupBy(
            tradeCorpEntity.stockCode,
            tradeCorpEntity.stockName,
            nyCorpDetailEntity.openPrice,
            nyCorpDetailEntity.closePrice
        )
        .fetch()
        ;

    }

}
