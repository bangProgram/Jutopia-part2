package com.jbproject.jutopia.rest.entity;

import com.jbproject.jutopia.rest.dto.model.CorpCisModel;
import com.jbproject.jutopia.rest.entity.key.CorpCisKey;
import com.jbproject.jutopia.rest.entity.key.NyCorpCisKey;
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
@Table(name = "tb_ny_corp_cis")
public class NyCorpCisEntity extends BaseEntity {

    @EmbeddedId
    private NyCorpCisKey id;


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

}
