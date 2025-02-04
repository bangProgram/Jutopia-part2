package com.jbproject.jutopia.rest.dto.result;

import lombok.Data;

import java.util.List;

@Data
public class TopMenuResult {

    private Integer menuSize;
    private List<MenuResult> menuList;
}
