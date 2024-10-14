package com.jbproject.jutopia.auth.model;

import com.jbproject.jutopia.rest.entity.RoleMenuRelation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RoleBasedWhiteList {

    private String role;
    private String url;
}
