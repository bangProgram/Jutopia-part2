package com.jbproject.jutopia.rest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.jbproject.jutopia.common.CommonUtils;
import com.jbproject.jutopia.rest.dto.model.NyStockModel;
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

    @Comment(value = "티커 코드")
    private String tickerSymbol;
    @Comment(value = "티커 명") //
    private String tickerName;


    @Comment(value = "종목 명") // entityName
    private String stockName;
    @Comment(value = "주식시장 타입")
    private String stockType;
    @Comment(value = "국가 타입")
    private String nationType;

    @Comment(value = "산업 코드")
    private String industryCode;

    @Comment(value = "시가")
    private Double openPrice;
    @Comment(value = "종가")
    private Double closePrice;
    @Comment(value = "누적 거래량")
    private Double accumulatedTradingVolume;
    @Comment(value = "누적 거래액 (달러)")
    private Double accumulatedTradingValue;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Comment(value = "현지 거래시간")
    private LocalDateTime localTradedAt;

    @Comment(value = "마켓 상태")
    private String marketStatus;
    @Comment(value = "시가총액(달러)")
    private Double marketValue;
    @Comment(value = "상장 주식수") // WeightedAverageNumberOfSharesOutstandingBasic
    private Double sharesVolume;
    @Comment(value = "주당 배당금") // CommonStockDividendsPerShareDeclared
    private Double dividend;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Comment(value = "배당 지급일")
    private LocalDateTime dividendPayAt;

}
