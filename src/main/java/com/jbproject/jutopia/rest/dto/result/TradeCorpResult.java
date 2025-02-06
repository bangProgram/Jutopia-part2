package com.jbproject.jutopia.rest.dto.result;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TradeCorpResult {

    private Long id;

    private String stockCode;
    private String stockName;
    private Long quantity;
    private Double avgAmount;
    private Long profitLossAmount;

    private LocalDateTime modifyDate;
}
