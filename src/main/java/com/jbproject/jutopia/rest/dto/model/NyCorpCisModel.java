package com.jbproject.jutopia.rest.dto.model;

import com.jbproject.jutopia.rest.entity.NyCorpCisEntity;
import com.jbproject.jutopia.rest.entity.key.NyCorpCisKey;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NyCorpCisModel {
    private String cikCode;
    private String bsnsYear;
    private String quarterlyReportCode;
    private String accountId;
    private String quarterlyReportName;
    private LocalDate closingDate;
    private String accountName;
    private String netAmount;
    private String accumulatedNetAmount;
    private String befNetAmount;
    private String befAccumulatedNetAmount;
    private String currency;

    public static NyCorpCisEntity create(NyCorpCisModel model) {
        NyCorpCisEntity entity = new NyCorpCisEntity();

        NyCorpCisKey key = NyCorpCisKey.builder()
                .cikCode(model.getCikCode())
                .bsnsYear(model.getBsnsYear())
                .quarterlyReportCode(model.getQuarterlyReportCode())
                .accountId(model.getAccountId())
                .build();

        entity.setId(key);
        entity.setQuarterlyReportName(model.getQuarterlyReportName());
        entity.setClosingDate(model.getClosingDate());
        entity.setAccountName(model.getAccountName());
        entity.setNetAmount(Double.parseDouble(model.getNetAmount()));
        entity.setCurrency(model.getCurrency());

        return entity;
    }
}
