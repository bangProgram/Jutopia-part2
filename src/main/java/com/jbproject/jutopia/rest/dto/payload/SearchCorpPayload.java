package com.jbproject.jutopia.rest.dto.payload;

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
