package com.jbproject.jutopia.rest.repository.custom.impl;

import com.jbproject.jutopia.rest.repository.custom.MenuCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MenuCustomImpl implements MenuCustom {

    private final JPAQueryFactory queryFactory;

}
