package com.jbproject.jutopia.rest.entity;

import com.jbproject.jutopia.rest.entity.key.CorpCisStatKey;
import com.jbproject.jutopia.rest.dto.CorpCisStatModel;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_corp_cis_stat")
public class CorpCisStatEntity extends BaseEntity implements Persistable<CorpCisStatKey> {

    @EmbeddedId
    private CorpCisStatKey id;

    private String stockName;
    private String accountName;
    private String befBsnsYear;
    private String befQuarterlyReportCode;
    private Long befNetAmount;
    private Long netAmount;
    private Long accumulatedNetAmount;
    private Long befYearAccumulatedNetAmount;

    @Builder
    public CorpCisStatEntity(
        CorpCisStatKey key
        , String stockName, String accountName, String befBsnsYear, String befQuarterlyReportCode, Long befNetAmount
        , Long netAmount, Long accumulatedNetAmount, Long befYearAccumulatedNetAmount
    ){
        this.id = key;
        this.stockName = stockName;
        this.accountName = accountName;
        this.befBsnsYear = befBsnsYear;
        this.befQuarterlyReportCode = befQuarterlyReportCode;
        this.befNetAmount = befNetAmount;
        this.netAmount = netAmount;
        this.accumulatedNetAmount = accumulatedNetAmount;
        this.befYearAccumulatedNetAmount = befYearAccumulatedNetAmount;
    }

    public void update(CorpCisStatModel model){
        this.stockName = model.getStockName();
        this.accountName = model.getAccountName();
        this.befBsnsYear = model.getBefBsnsYear();
        this.befQuarterlyReportCode = model.getBefQuarterlyReportCode();
        this.befNetAmount = model.getBefNetAmount();
        this.netAmount = model.getNetAmount();
        this.accumulatedNetAmount = model.getAccumulatedNetAmount();
        this.befYearAccumulatedNetAmount = model.getBefYearAccumulatedNetAmount();
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
