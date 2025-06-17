package com.jbproject.jutopia.rest.dto.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SearchTradeCorpPayload {

    @Schema(description = "티커명 혹은 종목명 검색 필드")
    private String searchWord;

}
