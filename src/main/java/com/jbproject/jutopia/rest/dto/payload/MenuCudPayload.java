package com.jbproject.jutopia.rest.dto.payload;

import lombok.Data;

@Data
public class MenuCudPayload {
    private Long menuId = 0L;
    private String menuName;
    private String menuDetail;
    private String menuUrl;
    private String useYn = "Y";
    private int seq = 1;
    private Long parentId;
    private String menuType;
    private String showYn = "N";
}
