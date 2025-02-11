package com.jbproject.jutopia.rest.service.impl;

import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.constant.ServerErrorCode;
import com.jbproject.jutopia.exception.ExceptionProvider;
import com.jbproject.jutopia.rest.dto.model.NyStockModel;
import com.jbproject.jutopia.rest.dto.payload.*;
import com.jbproject.jutopia.rest.dto.result.PostResult;
import com.jbproject.jutopia.rest.dto.result.ReplyResult;
import com.jbproject.jutopia.rest.dto.result.TradeCorpResult;
import com.jbproject.jutopia.rest.entity.NyCorpDetailEntity;
import com.jbproject.jutopia.rest.entity.PostEntity;
import com.jbproject.jutopia.rest.entity.ReplyEntity;
import com.jbproject.jutopia.rest.entity.relation.PostReplyRelation;
import com.jbproject.jutopia.rest.repository.*;
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
    private final NyCorpDetailRepository nyCorpDetailRepository;

    public List<TradeCorpResult> searchTradeCorpList(SearchTradeCorpPayload payload){
        return tradeCorpRepository.searchTradeCorpList(payload);
    }

    public List<NyStockModel> searchNyCorpList(SearchNyCorpPayload payload){
        String searchWord = "%" + payload.getSearchWord() + "%";
        List<NyCorpDetailEntity> test =  nyCorpDetailRepository.findByStockCodeLikeOrStockNameLike(searchWord,searchWord);
//        List<NyStockModel> resultList = nyCorpDetailRepository.searchNyCorpList(payload).stream().map(NyStockModel::create).toList();

        return null;
    }
}
