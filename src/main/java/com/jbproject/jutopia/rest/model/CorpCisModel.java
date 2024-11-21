package com.jbproject.jutopia.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
@Schema(description = "기업 연결손익계산서 모델")
public class CorpCisModel {

    @JsonProperty("stock_code")
    @Schema(description = "기업 코드")
    private String stockCode;
    @JsonProperty("bsns_year")
    @Schema(description = "보고서 년도")
    private String bsnsYear;
    @JsonProperty("quarterly_report_code")
    @Schema(description = "보고서 분기 코드")
    private String quarterlyReportCode;
    @JsonProperty("account_id")
    @Schema(description = "계정 Id")
    private String accountId;

    @JsonProperty("quarterly_report_name")
    @Schema(description = "보고서 분기 명")
    private String quarterlyReportName;
    @JsonProperty("closing_date")
    @Schema(description = "결산 일")
    private LocalDate closingDate;
    @JsonProperty("account_name")
    @Schema(description = "계정 명")
    private String accountName;
    @JsonProperty("net_amount")
    @Schema(description = "당기 이익금")
    private Long netAmount;
    @JsonProperty("accumulated_net_amount")
    @Schema(description = "당기 누적 이익금")
    private Long accumulatedNetAmount;
    @JsonProperty("bef_net_amount")
    @Schema(description = "전기 이익금")
    private Long befNetAmount;
    @JsonProperty("bef_accumulated_net_amount")
    @Schema(description = "전기 누적 이익금")
    private Long befAccumulatedNetAmount;
    @JsonProperty("currency")
    @Schema(description = "통화")
    private String currency;
}
