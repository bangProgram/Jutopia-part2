package com.jbproject.jutopia.rest.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter @ToString
public class CorpModel {

    @JacksonXmlProperty(localName = "corp_code")
    private String corpCode;
    @JacksonXmlProperty(localName = "corp_name")
    private int corpName;
    @JacksonXmlProperty(localName = "stock_code")
    private String stockCode;
    @JacksonXmlProperty(localName = "modify_date")
    private String modifyDate;

}
