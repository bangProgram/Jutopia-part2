package com.jbproject.jutopia.rest.service;

import com.jbproject.jutopia.rest.dto.model.CorpDetailModel;

import java.util.List;

public interface UtilService {
    List<CorpDetailModel> getCorpDetailList(String stockName);
}
