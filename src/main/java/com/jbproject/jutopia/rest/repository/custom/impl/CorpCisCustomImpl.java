package com.jbproject.jutopia.rest.repository.custom.impl;

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
}
