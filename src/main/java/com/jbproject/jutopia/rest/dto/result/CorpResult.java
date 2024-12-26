package com.jbproject.jutopia.rest.dto.result;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CorpResult {

    private String corpCode;
    private String corpName;
    private String stockCode;
    private LocalDate modifyDate;

}
