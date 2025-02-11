package com.jbproject.jutopia.rest.dto.result;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TradeCorpResult {

    private Long id;

    private String stockCode;
    private String stockName;
    private Double befDaysOpenPrice;
    private Double befDaysClosePrice;
    private Long quantity;
    private Double avgAmount;
    private Double totalProfitLossAmount;

    private LocalDateTime modifyDate;
}
