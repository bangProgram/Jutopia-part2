package com.jbproject.jutopia.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jbproject.jutopia.rest.entity.relation.RoleMenuRelation;

public class RoleMenuRTestModel {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("roleId")
    private String roleId;
    @JsonProperty("menuId")
    private Long menuId;
    @JsonProperty("isCud")
    private String isCud;

    public Long getId() {return this.id;}
    public String getRoleId() {return this.roleId; }
    public Long getMenuId() {return this.menuId; }
    public String getIsCud() {return this.isCud; }

    public RoleMenuRTestModel(){}

    public RoleMenuRTestModel(RoleMenuRelation entity){
        this.id = entity.getId();
        this.roleId = entity.getRoleId();
        this.menuId = entity.getMenuId();
        this.isCud = entity.getIsCud();
    }
}
