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
import org.hibernate.annotations.Comment;
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
    @Comment("분기 보고서 이름")
    private String quarterlyReportName;
    @Column(name = "closing_date")
    @Comment("회계 일자")
    private LocalDate closingDate;
    @Column(name = "account_name")
    @Comment("계정 명")
    private String accountName;
    @Column(name = "net_amount")
    @Comment("당기 금액")
    private Long netAmount;
    @Column(name = "accumulated_net_amount")
    @Comment("당기 누적 금액")
    private Long accumulatedNetAmount;
    @Column(name = "bef_net_amount")
    @Comment("전기 금액")
    private Long befNetAmount;
    @Column(name = "bef_accumulated_net_amount")
    @Comment("전기 누적 금액")
    private Long befAccumulatedNetAmount;
    @Column(name = "currency")
    @Comment("통화")
    private String currency;

}
