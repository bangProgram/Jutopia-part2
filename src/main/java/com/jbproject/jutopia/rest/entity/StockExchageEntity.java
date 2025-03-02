package com.jbproject.jutopia.rest.entity;

import com.jbproject.jutopia.rest.dto.model.NyStockModel;
import com.jbproject.jutopia.rest.entity.key.CorpCisKey;
import com.jbproject.jutopia.rest.entity.key.StockExchangeKey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

@Entity @Getter @Setter
@NoArgsConstructor
@Table(name = "tb_stock_exchange")
public class StockExchageEntity extends BaseEntity {

    @EmbeddedId
    private StockExchangeKey key;

    private Long startTime;
    private Long endTime;
    private Long closePriceSendTime;
    private String ExchangeNameKor;
    private String ExchangeNameEng;
    private String nationName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reuters_code")
    private NyCorpDetailEntity nyCorpDetailEntity;

    public StockExchageEntity(NyStockModel.StockExchangeType stockExchangeType){

        this.key = StockExchangeKey.builder()
                .nationType(stockExchangeType.getNationType())
                .exchangeCode(stockExchangeType.getCode())
                .build();

        this.startTime = stockExchangeType.getStartTime();
        this.endTime = stockExchangeType.getEndTime();
        this.closePriceSendTime = stockExchangeType.getClosePriceSendTime();
        this.ExchangeNameKor = stockExchangeType.getNameKor();
        this.ExchangeNameEng = stockExchangeType.getNameEng();
        this.nationName = stockExchangeType.getNationName();

    }
}
