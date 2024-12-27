package com.jbproject.jutopia.rest.entity;

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
@Table(name = "tb_ny_corp")
public class NyCorpEntity extends BaseEntity implements Persistable<String> {

    @Id
    private String reutersCode;

    private String stockCode;
    private String stockName;
    private LocalDate modifyDate;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "nyCorpEntity")
    private NyCorpDetailEntity nyCorpDetailEntity;

    @Builder
    public NyCorpEntity(String reutersCode, String stockCode, String stockName, LocalDate modifyDate){
        this.reutersCode = reutersCode;
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.modifyDate = modifyDate;
    }

    @Override
    public String getId() {
        return reutersCode;
    }

    @Override
    public boolean isNew() {
        return getCreateDttm() == null;
    }

}
