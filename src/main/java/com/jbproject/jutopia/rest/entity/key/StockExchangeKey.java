package com.jbproject.jutopia.rest.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Getter @Setter @Slf4j
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockExchangeKey implements Serializable {

    @Column( name = "nation_type",columnDefinition="varchar(10)")
    private String nationType;
    @Column(name = "exchange_code",columnDefinition="varchar(10)")
    private String exchangeCode;

}
