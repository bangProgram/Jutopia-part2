package com.jbproject.jutopia.rest.entity;

import com.jbproject.jutopia.rest.dto.model.NyStockModel;
import com.jbproject.jutopia.rest.entity.key.CorpCisKey;
import com.jbproject.jutopia.rest.entity.key.StockExchangeKey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
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
}
