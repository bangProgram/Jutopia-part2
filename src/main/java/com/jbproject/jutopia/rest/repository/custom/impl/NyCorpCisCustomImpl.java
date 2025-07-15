package com.jbproject.jutopia.rest.repository.custom.impl;

import com.jbproject.jutopia.rest.entity.key.NyCorpCisKey;
import com.jbproject.jutopia.rest.repository.custom.NyCorpCisCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jbproject.jutopia.rest.entity.QNyCorpCisEntity.nyCorpCisEntity;

@RequiredArgsConstructor
@Repository
public class NyCorpCisCustomImpl implements NyCorpCisCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public List<NyCorpCisKey> getNyCorpCisKey(String cikCode){

        return jpaQueryFactory.select(
                Projections.fields(
                        NyCorpCisKey.class,
                        nyCorpCisEntity.id.cikCode,
                        nyCorpCisEntity.id.accountId,
                        nyCorpCisEntity.id.bsnsYear,
                        nyCorpCisEntity.id.quarterlyReportCode
                )
        ).from(nyCorpCisEntity)
        .where(nyCorpCisEntity.id.cikCode.eq(cikCode))
        .orderBy(nyCorpCisEntity.id.bsnsYear.asc(), nyCorpCisEntity.id.quarterlyReportCode.asc())
        .fetch();
    }
}
