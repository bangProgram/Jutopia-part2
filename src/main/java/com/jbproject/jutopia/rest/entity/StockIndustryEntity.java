package com.jbproject.jutopia.rest.entity;

import com.jbproject.jutopia.rest.dto.model.NyStockModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.domain.Persistable;

@Entity @Getter @Setter
@NoArgsConstructor
@Table(name = "tb_stock_industry")
public class StockIndustryEntity extends BaseEntity implements Persistable<String> {

    @Id
    @Comment(value = "산업 코드")
    private String industryCode;
    @Comment(value = "산업 명 (한글)")
    private String industryGroupKor;
    @Comment(value = "산업 코드명")
    private String industryName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reuters_code")
    private NyCorpDetailEntity nyCorpDetailEntity;

    public StockIndustryEntity(NyStockModel.IndustryCodeType model){
        this.industryCode = model.getCode();
        this.industryGroupKor = model.getIndustryGroupKor();
        this.industryName = model.getName();
    }

    @Override
    public String getId() {
        return industryCode;
    }

    @Override
    public boolean isNew() {
        return getCreateDttm() == null;
    }
}
