package com.jbproject.jutopia.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jbproject.jutopia.rest.entity.RoleEntity;
import groovy.transform.builder.Builder;
import jakarta.persistence.Column;

public class RoleTestModel {
    @JsonProperty("role")
    private String role;
    @JsonProperty("roleName")
    private String roleName;

    public String getRole(){return this.role;}
    public String getRoleName(){return this.roleName;}

    public RoleTestModel(){}
    public RoleTestModel(RoleEntity entity) {
        this.role = entity.getRole();
        this.roleName = entity.getRoleName();
    }
}
