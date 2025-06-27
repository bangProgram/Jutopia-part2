package com.jbproject.jutopia.rest.service.impl;

import com.jbproject.jutopia.rest.dto.model.NaverNyStockModel;
import com.jbproject.jutopia.rest.dto.payload.*;
import com.jbproject.jutopia.rest.dto.result.TradeCorpResult;
import com.jbproject.jutopia.rest.repository.*;
import com.jbproject.jutopia.rest.service.UserInvestService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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

    public List<NaverNyStockModel> searchNyCorpList(SearchNyCorpPayload payload){
        String searchWord = "%" + payload.getSearchWord() + "%";
//        List<NyCorpDetailEntity> test =  nyCorpDetailRepository.findByStockCodeLikeOrStockNameLike(searchWord,searchWord);
//        List<NyStockModel> resultList = nyCorpDetailRepository.searchNyCorpList(payload).stream().map(NyStockModel::create).toList();

        return null;
    }
}
