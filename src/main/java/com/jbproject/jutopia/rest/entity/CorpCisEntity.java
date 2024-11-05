package com.jbproject.jutopia.rest.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tb_corp_cis")
public class CorpCisEntity extends BaseEntity implements Persistable<String> {

    @Id
    @Column(name = "corp_code")
    private String corpCode;

    @Column(name = "bsns_year")
    private String bsnsYear;
    @Column(name = "report_code")
    private String reportCode;
    @Column(name = "report_name")
    private String reportName;
    @Column(name = "closing_date")
    private LocalDate closingDate;
    @Column(name = "sj_div")
    private String sjDiv;
    @Column(name = "sj_nm")
    private String sjNm;
    @Column(name = "account_id")
    private String accountId;
    @Column(name = "account_name")
    private String accountName;
    @Column(name = "net_amount")
    private String netAmount;
    @Column(name = "accumulated_net_amount")
    private String accumulatedNetAmount;
    @Column(name = "bef_net_amount")
    private String befNetAmount;
    @Column(name = "bef_accumulated_net_amount")
    private String befAccumulatedNetAmount;
    @Column(name = "currency")
    private String currency;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corp_code", insertable = false, updatable = false)
    private CorpDetailEntity corpDetailEntity;

    @Override
    public String getId() {
        return corpCode;
    }

    @Override
    public boolean isNew() {
        return getCreateDttm() == null;
    }

}
