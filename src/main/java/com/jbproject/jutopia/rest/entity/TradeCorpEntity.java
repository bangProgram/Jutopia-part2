package com.jbproject.jutopia.rest.entity;

import com.jbproject.jutopia.rest.entity.relation.UserTradeCorpRelation;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_trade_corp")
public class TradeCorpEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stockCode;
    private String stockName;
    private Long quantity;
    private Double avgAmount;
    private Long profitLossAmount;

    private LocalDateTime modifyDate;

    @OneToMany(mappedBy = "tradeCorpEntity")
    private List<UserTradeCorpRelation> userTradeCorpRelations;

    @OneToMany(mappedBy = "tradeCorpEntity")
    private List<TradeCorpDetailEntity> userTradeCorpDetailEntities;
}
