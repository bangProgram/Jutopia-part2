package com.jbproject.jutopia.rest.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_role")
public class RoleEntity extends BaseEntity {

    @Id
    private String role;
    @Column(name = "role_name")
    private String roleName;

    @OneToMany(mappedBy = "roleEntity")
    private List<RoleMenuRelation> roleMenuRelations;


    @Builder
    public RoleEntity(String role, String roleName){
        this.role = role;
        this.roleName = roleName;
    }
}
