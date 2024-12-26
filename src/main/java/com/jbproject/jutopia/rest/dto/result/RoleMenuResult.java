package com.jbproject.jutopia.rest.dto.result;

import lombok.Data;

@Data
public class RoleMenuResult {

    private String roleId;
    private Long menuId;
    private String menuUrl;
    private String isCud;
}
