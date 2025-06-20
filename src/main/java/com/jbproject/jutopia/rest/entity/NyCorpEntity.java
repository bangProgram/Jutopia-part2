package com.jbproject.jutopia.rest.entity;

import com.jbproject.jutopia.rest.dto.model.NyStockModel;
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
public class NyCorpEntity extends BaseEntity {

    @Id
    private String reutersCode;

    /** NASDAQ / NYSE 티커 */
    @Column(length = 12, unique = true)
    private String stockCode;
    private String stockName;
    private LocalDate modifyDate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "nyCorp")
    private NyCorpDetailEntity nyCorpDetail;

    @Builder
    public NyCorpEntity(String reutersCode, String stockCode, String stockName, LocalDate modifyDate){
        this.reutersCode = reutersCode;
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.modifyDate = modifyDate;
    }

    public NyCorpEntity(NyStockModel model){
        this.reutersCode = model.getReutersCode();
        this.stockCode = model.getReutersCode();
        this.stockName = model.getStockName();
    }

}
