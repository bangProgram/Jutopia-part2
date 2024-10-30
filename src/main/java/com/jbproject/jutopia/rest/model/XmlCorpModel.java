package com.jbproject.jutopia.rest.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@JacksonXmlRootElement(localName = "result")
@Getter @Setter @ToString
public class XmlCorpModel {

    @JacksonXmlProperty(localName = "list")
    private List<CorpModel> corpModels;
}
