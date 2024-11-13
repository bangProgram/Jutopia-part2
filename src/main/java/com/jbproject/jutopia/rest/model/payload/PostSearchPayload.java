package com.jbproject.jutopia.rest.model.payload;

import lombok.Data;

@Data
public class PostSearchPayload {

    private String searchType;
    private String searchText;
    private String searchStockCode;
    private String searchStockName;
}
