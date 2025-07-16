package com.jbproject.jutopia.rest.entity;

import com.jbproject.jutopia.config.TickerCikCache;
import com.jbproject.jutopia.rest.dto.model.NaverNyStockModel;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_ny_corp")
public class NyCorpEntity extends BaseEntity {

    @Id
    @Comment(value = "CIK 고유 코드")
    private String cikCode;

    @Comment(value = "로이터 코드 (구버전)")
    private String reutersCode;
    /** NASDAQ / NYSE 티커 */
    @Comment(value = "티커 코드")
    private String tickerSymbol;
    @Comment(value = "종목 명 (한글)")
    private String stockName;
    @Comment(value = "종목 명 (영어)")
    private String stockNameEng;

    @Comment(value = "거래소 코드 (NYS/NSQ)")
    private String stockExchangeCode;
    @Comment(value = "거래소 이름 (NYSE/NASDAQ)")
    private String stockExchangeName;

    private LocalDate modifyDate;

//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "nyCorp")
//    private NyCorpDetailEntity nyCorpDetail;

    @Builder
    public NyCorpEntity(String cikCode, String tickerSymbol, String stockName, LocalDate modifyDate){
        this.cikCode = cikCode;
        this.tickerSymbol = tickerSymbol;
        this.stockName = stockName;
        this.modifyDate = modifyDate;
    }

    public NyCorpEntity(NaverNyStockModel model){
        String ticker = model.getSymbolCode();
        this.cikCode = TickerCikCache.tickerToCik(ticker.replace(".","-"));
        this.reutersCode = model.getReutersCode();
        this.tickerSymbol = model.getSymbolCode();
        this.stockName = model.getStockName();
        this.stockNameEng = model.getStockNameEng();

        this.stockExchangeCode = model.getStockExchangeType().getCode();
        this.stockExchangeName = model.getStockExchangeType().getName();
    }
}
