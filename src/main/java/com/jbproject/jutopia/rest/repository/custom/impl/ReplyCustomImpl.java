package com.jbproject.jutopia.rest.repository.custom.impl;

import com.jbproject.jutopia.rest.model.result.ReplyResult;
import com.jbproject.jutopia.rest.repository.custom.ReplyCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.jbproject.jutopia.rest.entity.QReplyEntity.replyEntity;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ReplyCustomImpl implements ReplyCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public List<ReplyResult> getReplyListBySupperId(Long supperId){

        return jpaQueryFactory.select(
            Projections.fields(
                ReplyResult.class,
                replyEntity.id.as("replyId"),
                replyEntity.replyDetail,
                replyEntity.replyWriter,
                replyEntity.supperId,
                replyEntity.replyDepth
            )
        ).from(replyEntity)
        .where(replyEntity.supperId.eq(supperId))
        .orderBy(replyEntity.id.asc())
        .fetch();
    }
}
