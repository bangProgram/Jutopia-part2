package com.jbproject.jutopia.rest.model.result;

import lombok.Data;

@Data
public class MenuResult {

    private Long id;
    private String menuName;
    private String menuUrl;
    private String useYn;
    private int seq;
    private Long parentId;
}
