package com.jbproject.jutopia.rest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.jbproject.jutopia.common.CommonUtils;
import com.jbproject.jutopia.config.TickerCikCache;
import com.jbproject.jutopia.rest.dto.model.NaverNyStockModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_ny_corp_detail")
public class NyCorpDetailEntity extends BaseEntity {

    @Id
    @Comment(value = "CIK 고유 코드") // cik
    private String cikCode;
    @Comment(value = "로이터 코드 (구버전)")
    private String reutersCode;
    @Comment(value = "티커 코드")
    private String tickerSymbol;
    @Comment(value = "종목 명 (한글)")
    private String stockName;
    @Comment(value = "종목 명 (영어)")
    private String stockNameEng;

    @Comment(value = "주식시장 타입")
    private String stockType;
    @Comment(value = "국가 타입")
    private String nationType;
    @Comment(value = "로이터 산업 코드")
    private String reutersIndustryCode;
    @Comment(value = "시가")
    private Double openPrice;
    @Comment(value = "종가")
    private Double closePrice;

    @Comment(value = "시가 종가 변동금액")
    private Double compareToPreviousClosePrice;
    @Comment(value = "시가 종가 변동비율")
    private Double fluctuationsRatio;
    @Comment(value = "executedVolume (확인불가)")
    private String executedVolume;
    @Comment(value = "누적 거래량")
    private Double accumulatedTradingVolume;
    @Comment(value = "누적 거래액 (달러)")
    private Double accumulatedTradingValue;

    @Comment(value = "누적 거래액 (원화)")
    private String accumulatedTradingValueKrwHangeul;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Comment(value = "현지 거래시간")
    private LocalDateTime localTradedAt;
    @Comment(value = "마켓 상태")
    private String marketStatus;
    @Comment(value = "overMarketPriceInfo")
    private String overMarketPriceInfo;
    @Comment(value = "시가총액(달러)")
    private Double marketValue;

    @Comment(value = "시가총액(달러번역)")
    private String marketValueHangeul;
    @Comment(value = "시가총액(원화)")
    private String marketValueKrwHangeul;
    @Comment(value = "배당")
    private Double dividend;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Comment(value = "배당 지급일")
    private LocalDateTime dividendPayAt;
    @Comment(value = "네이버증권 url")
    private String endUrl;

    @Comment(value = "지연시간")
    private String delayTime;
    @Comment(value = "지연시간 명")
    private String delayTimeName;
    @Comment(value = "네이버증권 url")
    private String stockEndUrl;
    @Comment(value = "exchangeOperatingTime")
    private Boolean exchangeOperatingTime;


    public NyCorpDetailEntity(NaverNyStockModel model){

        this.cikCode = TickerCikCache.tickerToCik(model.getSymbolCode());
        this.reutersCode = model.getReutersCode();
        this.tickerSymbol = model.getSymbolCode();
        this.stockName = model.getStockName();
        this.stockNameEng = model.getStockNameEng();
        this.stockType = model.getStockType();
        this.nationType = model.getNationType();
        this.reutersIndustryCode = model.getReutersIndustryCode();
        this.openPrice = CommonUtils.convertStringToDouble(model.getOpenPrice());
        this.closePrice = CommonUtils.convertStringToDouble(model.getClosePrice());
        this.compareToPreviousClosePrice = CommonUtils.convertStringToDouble(model.getCompareToPreviousClosePrice());
        this.fluctuationsRatio = CommonUtils.convertStringToDouble(model.getFluctuationsRatio());
        this.executedVolume = model.getExecutedVolume();
        this.accumulatedTradingVolume = CommonUtils.convertStringToDouble(model.getAccumulatedTradingVolume());
        this.accumulatedTradingValue = CommonUtils.convertStringToDouble(model.getAccumulatedTradingValue());
        this.accumulatedTradingValueKrwHangeul = model.getAccumulatedTradingValueKrwHangeul();
        this.localTradedAt = model.getLocalTradedAt();
        this.marketStatus = model.getMarketStatus();
        this.overMarketPriceInfo = model.getOverMarketPriceInfo();
        this.marketValue = CommonUtils.convertStringToDouble(model.getMarketValue());
        this.marketValueHangeul = model.getMarketValueHangeul();
        this.marketValueKrwHangeul = model.getMarketValueKrwHangeul();
        this.dividend = CommonUtils.convertStringToDouble(model.getDividend());
        this.dividendPayAt = model.getDividendPayAt();
        this.endUrl = model.getEndUrl();
        this.delayTime = model.getDelayTime();
        this.delayTimeName = model.getDelayTimeName();
        this.stockEndUrl = model.getStockEndUrl();
        this.exchangeOperatingTime = model.getExchangeOperatingTime();
    }

}

