package com.jbproject.jutopia.rest.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_corp")
public class CorpEntity extends BaseEntity implements Persistable<String> {

    @Id
    @Column(name = "corp_code")
    private String corpCode;

    @Column(name = "corp_name")
    private String corpName;
    @Column(name = "stock_code")
    private String stockCode;
    @Column(name = "modify_date")
    private LocalDate modifyDate;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "corpEntity")
    private CorpDetailEntity corpDetailEntity;

    @Builder
    public CorpEntity(String corpCode,String corpName,String stockCode,LocalDate modifyDate){
        this.corpCode = corpCode;
        this.corpName = corpName;
        this.stockCode = stockCode;
        this.modifyDate = modifyDate;
    }

    @Override
    public String getId() {
        return corpCode;
    }

    @Override
    public boolean isNew() {
        return getCreateDttm() == null;
    }

}
