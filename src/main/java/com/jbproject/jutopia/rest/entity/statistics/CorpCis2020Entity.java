package com.jbproject.jutopia.rest.entity.statistics;

import com.jbproject.jutopia.rest.entity.CorpCisStatTEntity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("2020")
public class CorpCis2020Entity extends CorpCisStatTEntity {

    private Long q1NetAmount;
    private Long q2NetAmount;
    private Long q3NetAmount;
    private Long q4NetAmount;
    private Long q1AccumulatedNetAmount;
    private Long q2AccumulatedNetAmount;
    private Long q3AccumulatedNetAmount;
    private Long q4AccumulatedNetAmount;
}
