package com.jbproject.jutopia.rest.dto.result;

import lombok.Data;

@Data
public class CommCodeResult {
    private String code;
    private String groupCode;

    private String codeName;
    private int seq;
    private String useYn;
}
