package com.jbproject.jutopia.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString @Builder
@Schema(description = "기업 연결손익계산서 통계 모델")
public class CorpCisStatModel {

    @JsonProperty("corpCode")
    @Schema(description = "기업 코드")
    private String stockCode;
    @JsonProperty("accountId")
    @Schema(description = "보고서 계정 ID")
    private String accountId;
    @JsonProperty("bsnsYear")
    @Schema(description = "보고 년도")
    private String bsnsYear;
    @JsonProperty("quarterlyReportCode")
    @Schema(description = "보고서 분기 코드")
    private String quarterlyReportCode;

    @JsonProperty("stockName")
    @Schema(description = "기업 명")
    private String stockName;
    @JsonProperty("accountName")
    @Schema(description = "보고서 계정명")
    private String accountName;
    @JsonProperty("befBsnsYear")
    @Schema(description = "전기 보고 년도")
    private String befBsnsYear;
    @JsonProperty("befQuarterlyReportCode")
    @Schema(description = "전기 보고서 분기 코드")
    private String befQuarterlyReportCode;
    @JsonProperty("befNetAmount")
    @Schema(description = "전기 금액")
    private Long befNetAmount;
    @JsonProperty("netAmount")
    @Schema(description = "당기 금액")
    private Long netAmount;
    @JsonProperty("accumulatedNetAmount")
    @Schema(description = "당기 누적 금액")
    private Long accumulatedNetAmount;
    @JsonProperty("befYearAccumulatedNetAmount")
    @Schema(description = "전년기 누적 금액")
    private Long befYearAccumulatedNetAmount;
}
