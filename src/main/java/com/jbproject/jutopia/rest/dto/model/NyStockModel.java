package com.jbproject.jutopia.rest.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NyStockModel {

    private String stockType;
    private String stockEndType;
    private String nationType;
    private String reutersCode;
    private String symbolCode;
    private String stockName;
    private String stockNameEng;
    private String reutersIndustryCode;
    private String openPrice;
    private String closePrice;
    private String compareToPreviousClosePrice;
    private String fluctuationsRatio;
    private String executedVolume;
    private String accumulatedTradingVolume;
    private String accumulatedTradingValue;
    private String accumulatedTradingValueKrwHangeul;
    private String localTradedAt;
    private String marketStatus;
    private String overMarketPriceInfo;
    private String marketValue;
    private String marketValueHangeul;
    private String marketValueKrwHangeul;
    private String dividend;
    private String dividendPayAt;
    private String endUrl;
    private String delayTime;
    private String delayTimeName;
    private String stockEndUrl;
    private String exchangeOperatingTime;

    private CompareToPreviousPrice compareToPreviousPrice;
    private StockExchangeType stockExchangeType;
    private IndustryCodeType industryCodeType;
    private CurrencyType currencyType;
    private TradeStopType tradeStopType;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CompareToPreviousPrice{
        private String code;
        private String text;
        private String name;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    public static class StockExchangeType{
        private String code;
        private String zoneId;
        private String nationType;
        private String delayTime;
        private String startTime;
        private String endTime;
        private String closePriceSendTime;
        private String nameKor;
        private String nameEng;
        private String nationName;
        private String stockType;
        private String nationCode;
        private String name;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    public static class IndustryCodeType{
        private String code;
        private String industryGroupKor;
        private String name;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    public static class CurrencyType{
        private String code;
        private String text;
        private String name;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    public static class TradeStopType{
        private String code;
        private String text;
        private String name;
    }
}
