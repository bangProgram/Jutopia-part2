package com.jbproject.jutopia.rest.dto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.jbproject.jutopia.rest.entity.NyCorpDetailEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverNyStockModel {

    @Schema(title = "주식시장 타입")
    private String stockType;
    @Schema(title = "주식시장 End 타입")
    private String stockEndType;
    @Schema(title = "국가 타입")
    private String nationType;
    @Schema(title = "로이터 코드")
    private String reutersCode;
    @Schema(title = "종목 코드")
    private String symbolCode;
    @Schema(title = "종목 티커")
    private String stockCode;
    @Schema(title = "종목 명 (한글)")
    private String stockName;
    @Schema(title = "종목 명 (영어)")
    private String stockNameEng;
    @Schema(title = "로이터 산업 코드")
    private String reutersIndustryCode;
    @Schema(title = "시가")
    private String openPrice;
    @Schema(title = "종가")
    private String closePrice;
    @Schema(title = "시가 종가 변동금액")
    private String compareToPreviousClosePrice;
    @Schema(title = "시가 종가 변동비율")
    private String fluctuationsRatio;
    @Schema(title = "executedVolume (확인불가)")
    private String executedVolume;
    @Schema(title = "누적 거래량")
    private String accumulatedTradingVolume;
    @Schema(title = "누적 거래액 (달러)")
    private String accumulatedTradingValue;
    @Schema(title = "누적 거래액 (원화)")
    private String accumulatedTradingValueKrwHangeul;

    @Schema(title = "현지 거래시간")
    private String localTradedAt;
    @Schema(title = "마켓 상태")
    private String marketStatus;
//    @Schema(title = "overMarketPriceInfo")
//    private String overMarketPriceInfo;
    @Schema(title = "시가총액(달러)")
    private String marketValue;
    @Schema(title = "시가총액(달러번역)")
    private String marketValueHangeul;
    @Schema(title = "시가총액(원화)")
    private String marketValueKrwHangeul;
    @Schema(title = "배당")
    private String dividend;

    @Schema(title = "배당 지급일")
    private String dividendPayAt;

    @Schema(title = "네이버증권 url")
    private String endUrl;
    @Schema(title = "지연시간")
    private String delayTime;
    @Schema(title = "지연시간 명")
    private String delayTimeName;
    @Schema(title = "네이버증권 url")
    private String stockEndUrl;
    @Schema(title = "exchangeOperatingTime")
    private Boolean exchangeOperatingTime;

    @Schema(title = "전일 종가 비교")
    private CompareToPreviousPrice compareToPreviousPrice;
    @Schema(title = "증권거래소 타입")
    private StockExchangeType stockExchangeType;
    @Schema(title = "산업 구분 타입")
    private IndustryCodeType industryCodeType;
    @Schema(title = "통화 타입")
    private CurrencyType currencyType;
    @Schema(title = "거래 종료 구분 타입")
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
        private Long delayTime;
        private Long startTime;
        private Long endTime;
        private Long closePriceSendTime;
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


    public static NaverNyStockModel create(NyCorpDetailEntity entity) {

        NaverNyStockModel result = new NaverNyStockModel();

        result.setReutersCode(entity.getReutersCode());
        result.setStockCode(entity.getStockType());
        result.setStockName(entity.getStockName());
        result.setStockNameEng(entity.getStockNameEng());
        result.setStockType(entity.getStockType());
        result.setNationType(entity.getNationType());
        result.setReutersIndustryCode(entity.getReutersIndustryCode());
        result.setOpenPrice(entity.getOpenPrice().toString());
        result.setClosePrice(entity.getClosePrice().toString());
        result.setCompareToPreviousClosePrice(entity.getCompareToPreviousClosePrice().toString());
        result.setFluctuationsRatio(entity.getFluctuationsRatio().toString());
        result.setExecutedVolume(entity.getExecutedVolume());
        result.setAccumulatedTradingVolume(entity.getAccumulatedTradingVolume().toString());
        result.setAccumulatedTradingValue(entity.getAccumulatedTradingValue().toString());
        result.setAccumulatedTradingValueKrwHangeul(entity.getAccumulatedTradingValueKrwHangeul());
        result.setLocalTradedAt(entity.getLocalTradedAt().toString());
        result.setMarketStatus(entity.getMarketStatus());
//        result.setOverMarketPriceInfo(entity.getOverMarketPriceInfo());
        result.setMarketValue(entity.getMarketValue().toString());
        result.setMarketValueHangeul(entity.getMarketValueHangeul());
        result.setMarketValueKrwHangeul(entity.getMarketValueKrwHangeul());
        result.setDividend(entity.getDividend().toString());
        result.setDividendPayAt(entity.getDividendPayAt().toString());
        result.setEndUrl(entity.getEndUrl());
        result.setDelayTime(entity.getDelayTime());
        result.setDelayTimeName(entity.getDelayTimeName());
        result.setStockEndUrl(entity.getStockEndUrl());
        result.setExchangeOperatingTime(entity.getExchangeOperatingTime());

        return result;
    }
}

