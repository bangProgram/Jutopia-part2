package com.jbproject.jutopia.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public enum ServerUtilConstant {

    CORP_GUBN("01","상장기업 기준")
    ,CORP_MERGE_LIMIT("900","merge limit 기준")
    ,CORP_MERGE_DELAY(String.valueOf(30*1000),"merge limit 기준")
    ;

    private String value;
    private String text;
}
