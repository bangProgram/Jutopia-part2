package com.jbproject.jutopia.rest.entity;

import com.jbproject.jutopia.rest.entity.key.CorpCisKey;
import com.jbproject.jutopia.rest.entity.key.CorpCisStatKey;
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

    private String account_name;
    private String bef_bsns_year;
    private String bef_quarterly_report_code;
    private Long bef_net_amount;
    private Long net_amount;

    @Builder
    public CorpCisStatEntity(
        String corpCode, String accountId, String bsnsYear,
        String quarterlyReportCode, String account_name, String bef_bsns_year,
        String bef_quarterly_report_code, Long bef_net_amount, Long net_amount
    ){
        this.id.setCorpCode(corpCode);
        this.id.setAccountId(accountId);
        this.id.setBsnsYear(bsnsYear);
        this.id.setQuarterlyReportCode(quarterlyReportCode);
        this.account_name = account_name;
        this.bef_bsns_year = bef_bsns_year;
        this.bef_quarterly_report_code = bef_quarterly_report_code;
        this.bef_net_amount = bef_net_amount;
        this.net_amount = net_amount;
    }

    public void update(String account_name,String bef_bsns_year,String bef_quarterly_report_code,Long bef_net_amount,Long net_amount){
        this.account_name = account_name;
        this.bef_bsns_year = bef_bsns_year;
        this.bef_quarterly_report_code = bef_quarterly_report_code;
        this.bef_net_amount = bef_net_amount;
        this.net_amount = net_amount;
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
