package com.jbproject.jutopia.rest.model.result;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class CommCodeResult {
    private String code;
    private String groupCode;

    private String codeName;
    private int seq;
    private String useYn;
}
