package com.jbproject.jutopia.rest.repository.custom.impl;

import com.jbproject.jutopia.rest.repository.custom.TradeCorpCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class TradeCorpCustomImpl implements TradeCorpCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public void getTradeCorpList() {

    }

}
