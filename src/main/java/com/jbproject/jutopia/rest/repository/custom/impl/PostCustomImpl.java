package com.jbproject.jutopia.rest.repository.custom.impl;

import com.jbproject.jutopia.rest.dto.payload.SearchPostPayload;
import com.jbproject.jutopia.rest.dto.result.PostResult;
import com.jbproject.jutopia.rest.repository.custom.PostCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.jbproject.jutopia.rest.entity.QPostEntity.postEntity;
import static org.springframework.util.StringUtils.hasText;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class PostCustomImpl implements PostCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public List<PostResult> searchPostList(SearchPostPayload payload){
        BooleanBuilder whereCondition = postListWhereCondition(payload);

        return jpaQueryFactory.select(
                Projections.fields(
                        PostResult.class,
                        postEntity.id.as("postId"),
                        postEntity.postType,
                        postEntity.postTitle,
                        postEntity.postDetail,
                        postEntity.stockCode,
                        postEntity.stockName,
                        postEntity.postWriter,
                        postEntity.postViewrs,
                        postEntity.createDttm
                )
        ).from(postEntity)
        .where(whereCondition)
        .orderBy(postEntity.createDttm.desc())
        .fetch();
    }

    private BooleanBuilder postListWhereCondition(SearchPostPayload payload){
        BooleanBuilder where = new BooleanBuilder();

        if(hasText(payload.getSearchText())){
            switch (payload.getSearchType()){
                case "01" :
                        where.and(postEntity.postTitle.like("%"+payload.getSearchText()+"%"));
                    break;
                case "02" :
                        where.and(postEntity.postTitle.like("%"+payload.getSearchText()+"%").or(postEntity.postDetail.like("%"+payload.getSearchText()+"%")));
                    break;
                case "03" :
                        where.and(postEntity.postWriter.like("%"+payload.getSearchText()+"%"));
                    break;
            }
        }

        if(hasText(payload.getPostType())){
            where.and(postEntity.postType.eq(payload.getPostType()));
        }

        if(hasText(payload.getSearchStockCode())){
            where.and(postEntity.stockCode.eq(payload.getSearchStockCode()));
        }

        return where;
    }
}
