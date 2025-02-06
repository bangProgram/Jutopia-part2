package com.jbproject.jutopia.rest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_trade_corp_detail")
public class TradeCorpDetailEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stockCode;
    private String stockName;

    private String investType;
    private LocalDateTime investDate;
    private Long quantity;
    private Long amount;

    private Long buyAmount;
    private Double buyFee;
    private Long sellAmount;
    private Double sellFee;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_corp_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TradeCorpEntity tradeCorpEntity;
}
