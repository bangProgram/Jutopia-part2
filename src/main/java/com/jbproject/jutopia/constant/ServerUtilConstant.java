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

    ,NY_CORP_STOCK_NYSE_PATH ("https://api.stock.naver.com/stock/exchange/NYSE/marketValue", "뉴욕거래소 url")
    ,NY_CORP_STOCK_NASDAQ_PATH ("https://api.stock.naver.com/stock/exchange/NASDAQ/marketValue", "나스닥거래소 url")
    ;

    private String value;
    private String text;
}
