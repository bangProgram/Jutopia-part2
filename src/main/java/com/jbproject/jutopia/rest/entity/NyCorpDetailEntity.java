package com.jbproject.jutopia.rest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_ny_corp_detail")
public class NyCorpDetailEntity extends BaseEntity implements Persistable<String> {

    @Id
    private String reutersCode;

    @Column(columnDefinition = "종목 코드")
    private String stockCode;
    @Column(columnDefinition = "종목 명 (한글)")
    private String stockName;
    @Column(columnDefinition = "종목 명 (영어)")
    private String stockNameEng;

    @Column(columnDefinition = "주식시장 타입")
    private String stockType;
    @Column(columnDefinition = "국가 타입")
    private String nationType;

    @Column(columnDefinition = "로이터 산업 코드")
    private String reutersIndustryCode;
    @Column(columnDefinition = "시가")
    private Double openPrice;
    @Column(columnDefinition = "종가")
    private Double closePrice;
    @Column(columnDefinition = "시가 종가 변동금액")
    private Double compareToPreviousClosePrice;
    @Column(columnDefinition = "시가 종가 변동비율")
    private Double fluctuationsRatio;
    @Column(columnDefinition = "executedVolume (확인불가)")
    private String executedVolume;
    @Column(columnDefinition = "누적 거래량")
    private Double accumulatedTradingVolume;
    @Column(columnDefinition = "누적 거래액 (달러)")
    private Double accumulatedTradingValue;
    @Column(columnDefinition = "누적 거래액 (원화)")
    private String accumulatedTradingValueKrwHangeul;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "현지 거래시간")
    private LocalDateTime localTradedAt;

    @Column(columnDefinition = "마켓 상태")
    private String marketStatus;
    @Column(columnDefinition = "overMarketPriceInfo")
    private String overMarketPriceInfo;
    @Column(columnDefinition = "시가총액(달러)")
    private Double marketValue;
    @Column(columnDefinition = "시가총액(달러번역)")
    private String marketValueHangeul;
    @Column(columnDefinition = "시가총액(원화)")
    private String marketValueKrwHangeul;
    @Column(columnDefinition = "배당")
    private Double dividend;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "배당 지급일")
    private LocalDateTime dividendPayAt;

    @Column(columnDefinition = "네이버증권 url")
    private String endUrl;
    @Column(columnDefinition = "지연시간")
    private String delayTime;
    @Column(columnDefinition = "지연시간 명")
    private String delayTimeName;
    @Column(columnDefinition = "네이버증권 url")
    private String stockEndUrl;
    @Column(columnDefinition = "exchangeOperatingTime")
    private Boolean exchangeOperatingTime;


    @OneToOne(fetch = FetchType.LAZY)
    private NyCorpEntity nyCorpEntity;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "nyCorpDetailEntity")
    private StockExchageEntity stockExchageEntity;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "nyCorpDetailEntity")
    private StockIndustryEntity stockIndustryEntity;

    @Override
    public String getId() {
        return reutersCode;
    }

    @Override
    public boolean isNew() {
        return getCreateDttm() == null;
    }

}
