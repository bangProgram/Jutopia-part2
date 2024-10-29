package com.jbproject.jutopia.rest.model.result;

import lombok.Data;

import java.util.List;

@Data
public class MenuResult {

    private Long menuId;
    private String roleId;

    private String menuName;
    private String menuDetail;
    private String menuUrl;
    private String useYn;
    private int seq;
    private Long parentId;
    private String menuType;
    private String isShowBar;

    private List<MenuResult> childMenu;

    private RoleMenuResult roleMenuResult;
}
