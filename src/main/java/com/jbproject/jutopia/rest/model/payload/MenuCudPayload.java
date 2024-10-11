package com.jbproject.jutopia.rest.model.payload;

import lombok.Data;

@Data
public class MenuCudPayload {
    private Long menuId = 0L;
    private String menuName;
    private String menuDetail;
    private String menuUrl;
    private String useYn = "Y";
    private int seq;
    private Long parentId;
    private String menuType;
}
