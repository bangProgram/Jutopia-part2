package com.jbproject.jutopia.rest.entity;

import com.jbproject.jutopia.config.TickerCikCache;
import com.jbproject.jutopia.rest.dto.model.NaverNyStockModel;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_ny_corp")
public class NyCorpEntity extends BaseEntity {

    @Id
    private String cikCode;

    /** NASDAQ / NYSE 티커 */
    private String tickerSymbol;
    private String stockName;
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
        this.cikCode = TickerCikCache.tickerToCik(model.getSymbolCode());
        this.tickerSymbol = model.getSymbolCode();
        this.stockName = model.getStockName();
    }
}
