package com.jbproject.jutopia.rest.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;



@JacksonXmlRootElement(localName = "list")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class CorpModel {

    @JacksonXmlProperty(localName = "corp_code")
    private String corpCode;
    @JacksonXmlProperty(localName = "corp_name")
    private String corpName;
    @JacksonXmlProperty(localName = "stock_code")
    private String stockCode;
    @JacksonXmlProperty(localName = "modify_date")
    private String modifyDate;

}
