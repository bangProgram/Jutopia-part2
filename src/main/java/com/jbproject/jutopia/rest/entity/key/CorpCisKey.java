package com.jbproject.jutopia.rest.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Getter @Setter
@Slf4j
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CorpCisKey implements Serializable {

    @Column( name = "stock_code",columnDefinition="varchar(10)")
    private String stockCode;
    @Column(name = "bsns_year",columnDefinition="varchar(10)")
    private String bsnsYear;
    @Column(name = "quarterly_report_code",columnDefinition="varchar(10)")
    private String quarterlyReportCode;
    @Column(name = "account_id")
    private String accountId;
}
