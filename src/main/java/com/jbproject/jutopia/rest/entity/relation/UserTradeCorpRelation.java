package com.jbproject.jutopia.rest.entity.relation;

import com.jbproject.jutopia.rest.entity.SocialEntity;
import com.jbproject.jutopia.rest.entity.TradeCorpEntity;
import com.jbproject.jutopia.rest.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_tradeCorp_rl")
public class UserTradeCorpRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_corp_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TradeCorpEntity tradeCorpEntity;
}
