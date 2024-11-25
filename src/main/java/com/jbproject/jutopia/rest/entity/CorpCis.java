package com.jbproject.jutopia.rest.entity;

import com.jbproject.jutopia.rest.entity.key.CorpCisStatKey;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
public interface CorpCis {
    @EmbeddedId
    CorpCisStatKey getId();
    String getQuarterlyReportName();
    LocalDate getClosingDate();
    String getAccountName();
    Long getNetAmount();
    Long getAccumulatedNetAmount();
    Long getBefNetAmount();
    Long getBefAccumulatedNetAmount();
    String getCurrency();
}
