package com.jbproject.jutopia.rest.model.payload;

import lombok.Data;

@Data
public class MergeCorpDetailPayload {
    private String gubn;
    private String corpCls;
    private String stLimit;
    private String edLimit;
}
