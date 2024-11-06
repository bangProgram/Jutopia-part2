package com.jbproject.jutopia.rest.model.payload;

import lombok.Data;

@Data
public class MergeCorpReportPayload {
    private String bsnsYear;
    private String reportType;
    private String quarterlyReportCode;
    private String quarterlyReportName;
}
