package com.jbproject.jutopia.rest.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter @Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockIndustryKey {

    @Column(name="industry_code")
    private String industryCode;
    @Column(name="nation_type", columnDefinition = "varchar(10)")
    private String nationType;

}
