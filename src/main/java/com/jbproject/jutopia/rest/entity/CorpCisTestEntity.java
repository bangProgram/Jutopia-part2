package com.jbproject.jutopia.rest.entity;

import com.jbproject.jutopia.rest.entity.key.CorpCisKey;
import com.jbproject.jutopia.rest.model.CorpCisModel;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@Table(name = "tb_corp_cis")
public class CorpCisTestEntity extends BaseEntity implements Persistable<CorpCisKey> {

    @EmbeddedId
    private CorpCisKey id;


    @Column(name = "quarterly_report_name")
    private String quarterlyReportName;
    @Column(name = "closing_date")
    private LocalDate closingDate;
    @Column(name = "account_name")
    private String accountName;
    @Column(name = "net_amount")
    private Long netAmount;
    @Column(name = "accumulated_net_amount")
    private Long accumulatedNetAmount;
    @Column(name = "bef_net_amount")
    private Long befNetAmount;
    @Column(name = "bef_accumulated_net_amount")
    private Long befAccumulatedNetAmount;
    @Column(name = "currency")
    private String currency;

    public CorpCisTestEntity(
            CorpCisModel model
    ){
        this.id = new CorpCisKey(model.getStockCode(),model.getBsnsYear(),model.getQuarterlyReportCode(),model.getAccountId());
        this.quarterlyReportName = model.getQuarterlyReportName();
        this.closingDate = model.getClosingDate();
        this.accountName = model.getAccountName();
        this.netAmount = model.getNetAmount();
        this.accumulatedNetAmount = model.getAccumulatedNetAmount();
        this.befNetAmount = model.getBefNetAmount();
        this.befAccumulatedNetAmount = model.getBefAccumulatedNetAmount();
        this.currency = model.getCurrency();
    }

    @Builder
    public CorpCisTestEntity(
            String stockCode,String bsnsYear,String quarterlyReportCode,
            String accountId,String quarterlyReportName,LocalDate closingDate,
            String accountName,Long netAmount,Long accumulatedNetAmount,
            Long befNetAmount,Long befAccumulatedNetAmount,String currency
    ){
            this.id = new CorpCisKey(stockCode,bsnsYear,quarterlyReportCode,accountId);
            this.quarterlyReportName = quarterlyReportName;
            this.closingDate = closingDate;
            this.accountName = accountName;
            this.netAmount = netAmount;
            this.accumulatedNetAmount = accumulatedNetAmount;
            this.befNetAmount = befNetAmount;
            this.befAccumulatedNetAmount = befAccumulatedNetAmount;
            this.currency = currency;
    }

    public void updateCorpCis(CorpCisModel model){
        this.quarterlyReportName = model.getQuarterlyReportName();
        this.closingDate = model.getClosingDate();
        this.accountName = model.getAccountName();
        this.netAmount = model.getNetAmount();
        this.accumulatedNetAmount = model.getAccumulatedNetAmount();
        this.befNetAmount = model.getBefNetAmount();
        this.befAccumulatedNetAmount = model.getBefAccumulatedNetAmount();
        this.currency = model.getCurrency();
    }

    @Override
    public CorpCisKey getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return getCreateDttm() == null;
    }

}
