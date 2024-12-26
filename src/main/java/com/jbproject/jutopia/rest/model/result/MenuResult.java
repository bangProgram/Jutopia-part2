package com.jbproject.jutopia.rest.model.result;

import com.jbproject.jutopia.rest.entity.MenuEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
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
    private String showYn;

    private List<MenuResult> childMenu;

    private RoleMenuResult roleMenuResult;

    public MenuResult(MenuEntity entity){

        this.menuId = entity.getId();
        this.menuName = entity.getMenuName();
        this.menuDetail = entity.getMenuDetail();
        this.menuUrl = entity.getMenuUrl();
        this.useYn = entity.getUseYn();
        this.seq = entity.getSeq();
        this.parentId = entity.getParentId();
        this.menuType = entity.getMenuType();
        this.showYn = entity.getShowYn();

        this.childMenu = entity.getChildMenu().stream().map(MenuResult::create).toList();
    }

    public static MenuResult create(MenuEntity entity){

        return new MenuResult(entity);
    }
}
