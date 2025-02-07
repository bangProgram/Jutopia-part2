package com.jbproject.jutopia.rest.service.impl;

import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.constant.ServerErrorCode;
import com.jbproject.jutopia.exception.ExceptionProvider;
import com.jbproject.jutopia.rest.dto.payload.*;
import com.jbproject.jutopia.rest.dto.result.PostResult;
import com.jbproject.jutopia.rest.dto.result.ReplyResult;
import com.jbproject.jutopia.rest.dto.result.TradeCorpResult;
import com.jbproject.jutopia.rest.entity.PostEntity;
import com.jbproject.jutopia.rest.entity.ReplyEntity;
import com.jbproject.jutopia.rest.entity.relation.PostReplyRelation;
import com.jbproject.jutopia.rest.repository.PostReplyRepository;
import com.jbproject.jutopia.rest.repository.PostRepository;
import com.jbproject.jutopia.rest.repository.ReplyRepository;
import com.jbproject.jutopia.rest.repository.TradeCorpRepository;
import com.jbproject.jutopia.rest.service.UserInvestService;
import com.jbproject.jutopia.rest.service.UserPostService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserInvestServiceImpl implements UserInvestService {

    private final TradeCorpRepository tradeCorpRepository;

    public List<TradeCorpResult> searchTradeCorpList(SearchTradeCorpPayload payload){
        return tradeCorpRepository.searchTradeCorpList(payload);
    }
}
