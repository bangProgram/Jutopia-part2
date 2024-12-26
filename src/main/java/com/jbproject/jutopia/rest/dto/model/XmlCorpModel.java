package com.jbproject.jutopia.rest.dto.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement(localName = "result")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class XmlCorpModel {

    @JacksonXmlProperty(localName = "list")
    @JacksonXmlElementWrapper( useWrapping = false)
    private List<CorpModel> corpModels = new ArrayList<>();
}
