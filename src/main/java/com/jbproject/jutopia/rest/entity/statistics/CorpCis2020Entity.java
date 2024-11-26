package com.jbproject.jutopia.rest.entity.statistics;

import com.jbproject.jutopia.rest.entity.BaseEntity;
import com.jbproject.jutopia.rest.entity.CorpCisStatTEntity;
import com.jbproject.jutopia.rest.entity.key.CorpCisStatTKey;
import com.jbproject.jutopia.rest.entity.key.CorpCisStatKey;
import com.jbproject.jutopia.rest.model.CorpCisModel;
import jakarta.persistence.DiscriminatorValue;
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
