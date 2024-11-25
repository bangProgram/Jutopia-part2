package com.jbproject.jutopia.rest.model.payload;

import lombok.Data;

import java.util.List;

@Data
public class SearchCorpPayload {

    private String stockCode;
    private String stockName;
    private List<String> accountIds;
    private String startBsnsYear;
    private String endBsnsYear;
    private String quarterlyType;
    private String growthRate;
}
