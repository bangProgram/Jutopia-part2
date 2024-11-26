package com.jbproject.jutopia.rest.model.result;

import com.jbproject.jutopia.rest.entity.CorpCisEntity;
import com.jbproject.jutopia.rest.model.CorpCisStatModel;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CorpCisResult {

    private String stockCode;
    private String stockName;
    private String accountId;
    private List<CorpCisStatModel> netAmoutList;

}
