package com.jbproject.jutopia.rest.model.payload;

import lombok.Data;

import java.util.List;

@Data
public class MergeCorpCisStatPayload {

    private String stockCode;
    private String accountId;
    private String bsnsYear;
    private String quarterlyReportCode;
    private List<String> corpCls;

}
