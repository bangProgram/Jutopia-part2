package com.jbproject.jutopia.rest.repository.custom.impl;

import com.jbproject.jutopia.rest.dto.payload.SearchNyCorpPayload;
import com.jbproject.jutopia.rest.entity.NyCorpDetailEntity;
import com.jbproject.jutopia.rest.repository.custom.NyCorpDetailCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jbproject.jutopia.rest.entity.QNyCorpDetailEntity.nyCorpDetailEntity;
import static com.jbproject.jutopia.rest.entity.QTradeCorpEntity.tradeCorpEntity;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
@Slf4j
public class NyCorpDetailCustomImpl implements NyCorpDetailCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public List<NyCorpDetailEntity> searchNyCorpList(SearchNyCorpPayload payload){

        BooleanBuilder whereCondition = new BooleanBuilder();
        if(hasText(payload.getSearchWord())){
            whereCondition.and(
                    nyCorpDetailEntity.stockCode.like("%"+payload.getSearchWord()+"%").or(
                    nyCorpDetailEntity.stockName.like("%"+payload.getSearchWord()+"%")
                )
            );
        }

        return jpaQueryFactory.select(nyCorpDetailEntity)
            .from(nyCorpDetailEntity)
            .where(whereCondition)
            .fetch()
            ;
    }
}
