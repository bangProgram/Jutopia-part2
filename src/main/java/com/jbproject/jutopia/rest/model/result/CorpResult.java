package com.jbproject.jutopia.rest.model.result;

import com.jbproject.jutopia.rest.entity.CorpDetailEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CorpResult {

    private String corpCode;
    private String corpName;
    private String stockCode;
    private LocalDate modifyDate;

}
