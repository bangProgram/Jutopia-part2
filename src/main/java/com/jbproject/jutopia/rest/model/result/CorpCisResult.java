package com.jbproject.jutopia.rest.model.result;

import com.jbproject.jutopia.rest.entity.CorpCisEntity;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CorpCisResult {

    private String stockCode;
    private String stockName;
    private String accountId;
    private String bsnsYear;
    private String quarterlyReportCode;
    private String quarterlyReportName;
    private LocalDate closingDate;
    private String accountName;
    private Long netAmount;
    private Long accumulatedNetAmount;
    private Long befNetAmount;
    private Long befAccumulatedNetAmount;
    private String currency;

}
