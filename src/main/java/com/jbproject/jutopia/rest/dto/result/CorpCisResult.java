package com.jbproject.jutopia.rest.dto.result;

import com.jbproject.jutopia.rest.dto.model.CorpCisStatModel;
import lombok.Data;

import java.util.List;

@Data
public class CorpCisResult {

    private String stockCode;
    private String stockName;
    private String accountId;
    private List<CorpCisStatModel> netAmoutList;

}
