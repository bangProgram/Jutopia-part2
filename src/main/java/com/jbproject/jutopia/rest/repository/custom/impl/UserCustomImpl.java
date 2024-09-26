package com.jbproject.jutopia.rest.repository.custom.impl;

import com.jbproject.jutopia.rest.entity.UserEntity;
import com.jbproject.jutopia.rest.repository.custom.UserCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static com.jbproject.jutopia.rest.entity.QUserEntity.userEntity;
import static com.jbproject.jutopia.rest.entity.QSocialEntity.socialEntity;
import static com.jbproject.jutopia.rest.entity.QUserSocialRelation.userSocialRelation;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserCustomImpl implements UserCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public UserEntity getUserInfo(String userId){

        return jpaQueryFactory.selectFrom(userEntity).where(userEntity.userId.eq(userId)).fetchOne();
    };
}
