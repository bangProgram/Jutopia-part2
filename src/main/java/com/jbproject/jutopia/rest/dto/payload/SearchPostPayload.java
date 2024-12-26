package com.jbproject.jutopia.rest.dto.payload;

import lombok.Data;

@Data
public class SearchPostPayload {

    private String postType;
    private String searchType;
    private String searchText;
    private String searchStockCode;
    private String searchStockName;
}
