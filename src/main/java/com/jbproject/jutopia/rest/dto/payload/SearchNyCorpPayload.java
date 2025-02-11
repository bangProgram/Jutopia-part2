package com.jbproject.jutopia.rest.dto.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class SearchNyCorpPayload {

    @Schema(description = "기업 통합겁색 Search Word")
    private String searchWord;

    @Schema(description = "기업 단일검색 Stock Code (티커명)")
    private String stockCode;
}
