package com.jbproject.jutopia.rest.service;

import com.jbproject.jutopia.rest.dto.model.NaverNyStockModel;
import com.jbproject.jutopia.rest.dto.payload.*;
import com.jbproject.jutopia.rest.dto.result.TradeCorpResult;

import java.util.List;

public interface UserInvestService {
    List<TradeCorpResult> searchTradeCorpList(SearchTradeCorpPayload payload);

    List<NaverNyStockModel> searchNyCorpList(SearchNyCorpPayload payload);
}
