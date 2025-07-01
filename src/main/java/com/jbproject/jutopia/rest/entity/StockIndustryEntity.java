package com.jbproject.jutopia.rest.entity;

import com.jbproject.jutopia.rest.dto.model.NaverNyStockModel;
import com.jbproject.jutopia.rest.entity.key.StockIndustryKey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity @Getter @Setter
@NoArgsConstructor
@Table(name = "tb_stock_industry")
public class StockIndustryEntity extends BaseEntity {

    @EmbeddedId
    private StockIndustryKey key;

    @Comment(value = "산업 명 (한글)")
    private String industryGroupKor;
    @Comment(value = "산업 코드명")
    private String industryName;


    public StockIndustryEntity(NaverNyStockModel model){
        NaverNyStockModel.IndustryCodeType industry = model.getIndustryCodeType();

        this.key = StockIndustryKey.builder()
                .industryCode(industry.getCode())
                .nationType(model.getNationType())
                .build();

        this.industryGroupKor = industry.getIndustryGroupKor();
        this.industryName = industry.getName();
    }

}
