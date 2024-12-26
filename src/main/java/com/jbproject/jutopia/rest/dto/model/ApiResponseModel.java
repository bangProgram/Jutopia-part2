package com.jbproject.jutopia.rest.dto.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseModel{

    private Long page;
    private Long pageSize;
    private Long totalCount;
    private List<NyStockModel> stocks;
    private String marketStatus;
    private String localOpenTimeDesc;
    private String localOpenTime;
}
