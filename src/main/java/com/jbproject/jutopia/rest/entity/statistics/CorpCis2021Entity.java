package com.jbproject.jutopia.rest.entity.statistics;

import com.jbproject.jutopia.rest.entity.BaseEntity;
import com.jbproject.jutopia.rest.entity.CorpCis;
import com.jbproject.jutopia.rest.entity.key.CorpCisStatKey;
import com.jbproject.jutopia.rest.model.CorpCisModel;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "tb_corp_cis_2021")
public class CorpCis2021Entity extends BaseEntity implements Persistable<CorpCisStatKey>, CorpCis {

    @EmbeddedId
    private CorpCisStatKey id;

    private String quarterlyReportName;
    private LocalDate closingDate;
    private String accountName;
    private Long netAmount;
    private Long accumulatedNetAmount;
    private Long befNetAmount;
    private Long befAccumulatedNetAmount;
    private String currency;

    public CorpCis2021Entity(
            CorpCisModel model
    ){
        this.id = new CorpCisStatKey(model.getStockCode(),model.getBsnsYear(),model.getQuarterlyReportCode(),model.getAccountId());
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
    public CorpCis2021Entity(
            String stockCode,String bsnsYear,String quarterlyReportCode,
            String accountId,String quarterlyReportName,LocalDate closingDate,
            String accountName,Long netAmount,Long accumulatedNetAmount,
            Long befNetAmount,Long befAccumulatedNetAmount,String currency
    ){
        this.id = new CorpCisStatKey(stockCode,bsnsYear,quarterlyReportCode,accountId);
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
    public CorpCisStatKey getId() {
        return id;
    }
    @Override
    public boolean isNew() {
        return getCreateDttm() == null;
    }
}
