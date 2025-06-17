package com.jbproject.jutopia.rest.repository.custom;

import com.jbproject.jutopia.rest.dto.payload.SearchTradeCorpPayload;
import com.jbproject.jutopia.rest.dto.result.TradeCorpResult;

import java.util.List;

public interface TradeCorpCustom {

    List<TradeCorpResult> searchTradeCorpList(SearchTradeCorpPayload payload);
}
